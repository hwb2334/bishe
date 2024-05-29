package com.shenmou.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.common.Constants;
import com.shenmou.springboot.entity.*;
import com.shenmou.springboot.entity.dto.RGoodsDTO;
import com.shenmou.springboot.entity.dto.RGoodsMallDTO;
import com.shenmou.springboot.mapper.MallMapper;
import com.shenmou.springboot.mapper.NormalMapper;
import com.shenmou.springboot.mapper.RGoodsMapper;
import com.shenmou.springboot.mapper.UserActionsMapper;
import com.shenmou.springboot.service.IRGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RGoodsServiceImpl extends ServiceImpl<RGoodsMapper, RGoods> implements IRGoodsService {

    @Resource
    private MallMapper mallMapper;
    @Resource
    private UserActionsMapper userActionsMapper;
    @Resource
    private NormalMapper normalMapper;
    // 用户行为列表
    private List<UserActions> uas;
    // 当前用户行为的反向索引表(gid=>index)
    private Map<Integer, Integer> curGMap;
    // 当前用户行为的商品的对应归一化信息
    private List<Normal> normals;
    // 当前用户行为的商品的对应归一化信息的反向索引表(gid=>index)
    private Map<Integer, Integer> curNormMap;

    @Override
    public List<RGoodsDTO> findDTOAll(String curAddress) {
        List<RGoods> rGoods = list();
        return change(findByAddress(curAddress, rGoods));
    }

    @Override
    public List<RGoodsDTO> findDTOByCateId(Integer cateId, String curAddress) {
        QueryWrapper<RGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cate_id", cateId);
        List<RGoods> rGoods = list(queryWrapper);
        return change(findByAddress(curAddress, rGoods));
    }

    @Override
    public RGoodsMallDTO findById(Integer rgoodsId, Integer userId) {
        RGoodsMallDTO rGoodsMallDTO = new RGoodsMallDTO();
        QueryWrapper<RGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rgoods_id", rgoodsId);
        RGoods good = getOne(queryWrapper);
        Mall mall = mallMapper.selectById(good.getMallId());
        rGoodsMallDTO.setGood(Collections.singletonList(good));
        rGoodsMallDTO.setMall(Collections.singletonList(mall));
        MallToGoods mallToGoods = new MallToGoods();
        mallToGoods.setMallId(mall.getMallId());
        mallToGoods.setIds(Collections.singletonList(good.getRgoodsId()));
        rGoodsMallDTO.setMallToGoods(Collections.singletonList(mallToGoods));
        // 记录用户点击数据
        QueryWrapper<UserActions> qw1 = new QueryWrapper<>();
        qw1.eq("user_id", userId);
        qw1.eq("rgoods_id", rgoodsId);
        UserActions ua = userActionsMapper.selectOne(qw1);
        if(ua == null){
            ua = new UserActions();
            ua.setUserId(userId);
            ua.setRgoodsId(rgoodsId);
            ua.setClicked(1);
            ua.setScore(Constants.DEFAULT_ACTION_SCORE);
            ua.setBuyed(0);
            userActionsMapper.insert(ua);
        }else{
            ua.setClicked(ua.getClicked() == null ? 1 : ua.getClicked() + 1);
            userActionsMapper.updateById(ua);
        }
        return rGoodsMallDTO;
    }

    @Override
    public List<RGoodsDTO> findRecDTO(Integer userId, String curAddress) {
        uas = userActionsMapper.findAll();
        // 当前用户评价信息
        List<UserActions> curActions = uas
                .stream()
                .filter(ua -> ua.getUserId().equals(userId))
                .collect(Collectors.toList());
        List<RGoods> list = findByAddress(curAddress, list());
        // curActions 为空不推荐
        // curActions 不为空开始推荐
        if (curActions != null && curActions.size() != 0) {
            List<RGoodsDTO> RGDTOS = curRecommend(list, curActions, userId);
            // 推荐商品数量为0 （用户把能买的所有商品都买过了）
            if(RGDTOS.size() == 0) return change(list.stream().limit(Constants.RECOMMAND_NUMS).collect(Collectors.toList()));
            else return curRecommend(list, curActions, userId);
        } else {
            List<RGoods> rec = list.stream().limit(Constants.RECOMMAND_NUMS).collect(Collectors.toList());
            return change(rec);
        }
    }

    @Override
    public List<RGoodsDTO> findByName(String goodName, String curAddress) {
        QueryWrapper<RGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("rgoods_name", goodName);
        List<RGoods> list = list(queryWrapper);
        return change(findByAddress(curAddress, list));
    }

    private List<RGoods> findByAddress(String address, List<RGoods> list){
        QueryWrapper<Mall> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("address", address);
        List<Integer> mallIds = mallMapper.selectList(queryWrapper)
                .stream()
                .map(Mall::getMallId)
                .collect(Collectors.toList());
        return list.stream()
                .filter(good -> mallIds.contains(good.getMallId()))
                .collect(Collectors.toList());
    }

    private List<RGoodsDTO> change(List<RGoods> rGoods) {
        List<RGoodsDTO> rGoodsDTOS = new ArrayList<>();
        for (RGoods rGood : rGoods) {
            RGoodsDTO rGoodsDTO = new RGoodsDTO();
            BeanUtil.copyProperties(rGood, rGoodsDTO, true);
            rGoodsDTOS.add(rGoodsDTO);
        }
        return rGoodsDTOS;
    }

    // 有行为数据
    private List<RGoodsDTO> curRecommend(List<RGoods> list, List<UserActions> curActions, Integer userId) {
        Set<Integer> curGIds = curActions
                .stream()
                .map(UserActions::getRgoodsId)
                .collect(Collectors.toSet());
        List<Integer> gIds = new ArrayList<>(curGIds);
        normals = normalMapper.findByGIds(gIds);
        curGMap = new HashMap<>();
        curNormMap = new HashMap<>();
        // 构建当前用户行为的商品的对应归一化信息的反向索引表(gid=>index)
        for (int i = 0; i < normals.size(); i++) {
            Normal normal = normals.get(i);
            curNormMap.put(normal.getRgoodsId(), i);
        }
        // 构建当前用户行为的反向索引表(gid=>index)
        for (int i = 0; i < uas.size(); i++) {
            UserActions ua = uas.get(i);
            Integer gId = ua.getRgoodsId();
            if (ua.getUserId().equals(userId) && curGIds.contains(gId)) {
                curGMap.put(gId, i);
            }
        }
        // 已经评分的商品Id数组
        Integer[] idsDone = curActions.stream().map(UserActions::getRgoodsId).toArray(Integer[]::new);
        // 未评分的商品Id数组
        Integer[] idsWait = list.stream()
                .filter(good -> Arrays.stream(idsDone).noneMatch(id -> id.equals(good.getRgoodsId())))
                .collect(Collectors.toList())
                .stream()
                .map(RGoods::getRgoodsId)
                .toArray(Integer[]::new);
        GoodWaitInfo goodWaitInfo = genWaitInfo(idsDone, idsWait);
        List<RGoods> rec = goodWaitInfo.getRec(Constants.RECOMMAND_NUMS, list);
        return change(rec);
    }

    // 获取推荐商品
    private GoodWaitInfo genWaitInfo(Integer[] idsDone, Integer[] idsWait) {
        SimilarityInfo simiInfo = genSimiInfo();
        Map<Integer, Integer> gmap = simiInfo.getGmap();
        Double[] simi = simiInfo.getSimi();
        int lenDone = idsDone.length;
        int lenWait = idsWait.length;
        Double[] scoreDone = new Double[lenDone];
        Double[] scoreWait = new Double[lenWait];
        for (int i = 0; i < lenDone; i++) {
            Integer gid = idsDone[i];
            Integer index = curGMap.get(gid);
            UserActions ua = uas.get(index);
            Double score = ua.getScore();
            Integer buyed = ua.getBuyed();
            Integer clicked = ua.getClicked();
            Integer inNor = curNormMap.get(gid);
            Normal normal = normals.get(inNor);
            scoreDone[i] = score / Constants.SCORE_WAY;
            int count = 1;
            if (normal.getMaxBuyed() - normal.getMinBuyed() != 0) {
                scoreDone[i] += ((double) (buyed - normal.getMinBuyed())) / (normal.getMaxBuyed() - normal.getMinBuyed());
                count++;
            }
            if (normal.getMaxClicked() - normal.getMinClicked() != 0) {
                scoreDone[i] += ((double) (clicked - normal.getMinClicked())) / (normal.getMaxClicked() - normal.getMinClicked());
                count++;
            }
            scoreDone[i] /= count;
        }
        for (int i = 0; i < lenWait; i++) {
            Integer idW = idsWait[i];
            scoreWait[i] = 0.0;
            if (gmap.get(idW) != null) {
                Double score = 0.0;
                for (int j = 0; j < lenDone; j++) {
                    Integer idD = idsDone[j];
                    Integer index = simiInfo.getIndex(gmap.get(idW), gmap.get(idD));
                    score += scoreDone[j] * simi[index];
                }
                score /= lenDone;
                scoreWait[i] = score;
            }
        }
        return new GoodWaitInfo(scoreWait, idsWait);
    }

    // 构建相似矩阵
    private SimilarityInfo genSimiInfo() {
        List<GUS> gusList = new ArrayList<>();
        UGMatrixInfo matrixInfo = genUGMI(gusList);
        int len = gusList.size();
        // 对角矩阵优化
        SimilarityInfo simiInfo = new SimilarityInfo((1 + len) * len / 2, len);
        Double[] simi = simiInfo.getSimi();
        simiInfo.setGmap(matrixInfo.getGmap());
        Map<Integer, Integer> gmap = matrixInfo.getGmap();
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                List<Integer> uids1 = gusList.get(i).getUid();
                Integer gid1 = gusList.get(i).getGid();
                Integer col1 = gmap.get(gid1);
                List<Integer> uids2 = gusList.get(j).getUid();
                Integer gid2 = gusList.get(j).getGid();
                Integer col2 = gmap.get(gid2);
                List<Integer> uids = genSame(uids1, uids2);
                Double sm = calSimi(matrixInfo, col1, col2, uids);
                Integer index = simiInfo.getIndex(col1, col2);
                simi[index] = sm;
            }
        }
        return simiInfo;
    }

    // 计算两个商品的相似度
    private Double calSimi(UGMatrixInfo matrixInfo, Integer col1, Integer col2, List<Integer> uids) {
        // 评分信息
        Double[][] ugScore = matrixInfo.getUgScore();
        Integer[][] ugBuyed = matrixInfo.getUgBuyed();
        Integer[][] ugClicked = matrixInfo.getUgClicked();
        Map<Integer, Integer> umap = matrixInfo.getUmap();
        Double rst = 0.0, x1 = 0.0, y1 = 0.0, z1 = 0.0, x2 = 0.0, y2 = 0.0, z2 = 0.0, x3 = 0.0, y3 = 0.0, z3 = 0.0;
        for (Integer uid : uids) {
            Integer row = umap.get(uid);
            // score
            x1 += ugScore[row][col1] * ugScore[row][col2];
            y1 += ugScore[row][col1] * ugScore[row][col1];
            z1 += ugScore[row][col2] * ugScore[row][col2];
            // buyed
            x2 += ugBuyed[row][col1] * ugBuyed[row][col2];
            y2 += ugBuyed[row][col1] * ugBuyed[row][col1];
            z2 += ugBuyed[row][col2] * ugBuyed[row][col2];
            // clicked
            x3 += ugClicked[row][col1] * ugClicked[row][col2];
            y3 += ugClicked[row][col1] * ugClicked[row][col1];
            z3 += ugClicked[row][col2] * ugClicked[row][col2];
        }
        if (uids.size() == 0) {
            return rst;
        } else {
            rst = (x1 / (Math.sqrt(y1) * Math.sqrt(z1))
                    + x2 / (Math.sqrt(y2) * Math.sqrt(z2))
                    + x3 / (Math.sqrt(y3) * Math.sqrt(z3))) / 3;
            return rst;
        }
    }

    // 双指针法算出都评价过某商品的用户
    private List<Integer> genSame(List<Integer> uids1, List<Integer> uids2) {
        List<Integer> rst = new ArrayList<>();
        int len1 = uids1.size();
        int len2 = uids2.size();
        int i = 0, j = 0;
        while (i < len1 && j < len2) {
            Integer uid1 = uids1.get(i);
            Integer uid2 = uids2.get(j);
            if (uid1.equals(uid2)) {
                rst.add(uid2);
                i++;
                j++;
            } else if (uid1 > uid2) {
                j++;
            } else if (uid1 < uid2) {
                i++;
            }
        }
        return rst;
    }

    // 构建UGMatrixInfo
    private UGMatrixInfo genUGMI(List<GUS> gusList) {
        List<Integer> uid = uas.stream()
                .map(UserActions::getUserId)
                .distinct()
                .collect(Collectors.toList());
        List<Integer> gid = uas.stream()
                .map(UserActions::getRgoodsId)
                .distinct()
                .collect(Collectors.toList());
        UGMatrixInfo matrixInfo = new UGMatrixInfo(uid.size(), gid.size());
        Map<Integer, Integer> umap = new HashMap<>();
        Map<Integer, Integer> gmap = new HashMap<>();
        for (int i = 0; i < uid.size(); i++)
            umap.put(uid.get(i), i);
        for (int i = 0; i < gid.size(); i++) {
            Integer id = gid.get(i);
            gmap.put(id, i);
            GUS gus = new GUS();
            gus.setGid(id);
            gusList.add(gus);
        }
        matrixInfo.setUmap(umap);
        matrixInfo.setGmap(gmap);
        Double[][] ugScore = matrixInfo.getUgScore();
        Integer[][] ugBuyed = matrixInfo.getUgBuyed();
        Integer[][] ugClicked = matrixInfo.getUgClicked();
        for (UserActions ua : uas) {
            Integer userId = ua.getUserId();
            Integer rgoodsId = ua.getRgoodsId();
            Integer uIndex = umap.get(userId);
            Integer gIndex = gmap.get(rgoodsId);
            ugScore[uIndex][gIndex] = ua.getScore();
            ugBuyed[uIndex][gIndex] = ua.getBuyed();
            ugClicked[uIndex][gIndex] = ua.getClicked();
            gusList.get(gIndex).getUid().add(userId);
        }
        return matrixInfo;
    }
}

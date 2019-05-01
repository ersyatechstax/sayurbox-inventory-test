package com.main.mapper.converter;

import com.main.persistence.domain.Base;
import com.main.vo.BaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ersya 29/04/2019
 * @param <T> Model class
 * @param <Z> ViewObject (VO) class
 */
public abstract class AbstractBaseVOConverter<T extends Base,Z extends BaseVO> implements InterfaceVOConverter<T,Z> {

    @Override
    public T transferVOToModel(Z vo, T model){
        return model;
    }

    @Override
    public Z transferModelToVO(T model, Z vo){
        vo.setId(model.getSecureId());
        vo.setVersion(model.getVersion());
        return vo;
    }

    public List<Z> transferListOfModelToListOfVO(List<T> models){
        List<Z> vos = new ArrayList<>();
        for(T model : models){
            vos.add(transferModelToVO(model , null));
        }
        return vos;
    }
}

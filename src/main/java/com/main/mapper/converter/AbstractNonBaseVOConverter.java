package com.main.mapper.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ersya 30/04/2019
 * @param <T> Model class
 * @param <Z> ViewObject (VO) class
 */
public abstract class AbstractNonBaseVOConverter<T,Z> implements InterfaceVOConverter<T,Z> {
    @Override
    public T transferVOToModel(Z vo, T model){
        return model;
    }

    @Override
    public Z transferModelToVO(T model, Z vo){
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

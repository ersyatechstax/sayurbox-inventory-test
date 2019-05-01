package com.main.mapper.converter;

public interface InterfaceVOConverter<T,Z> {

    public T transferVOToModel(Z vo, T model);

    public Z transferModelToVO(T model, Z vo);

}

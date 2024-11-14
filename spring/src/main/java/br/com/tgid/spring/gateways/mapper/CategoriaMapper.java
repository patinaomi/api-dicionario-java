package br.com.tgid.spring.gateways.mapper;

import br.com.tgid.spring.domains.Categoria;
import br.com.tgid.spring.gateways.request.CategoriaRequest;
import br.com.tgid.spring.gateways.response.CategoriaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {

    Categoria map(CategoriaRequest request);
    CategoriaResponse map(Categoria categoria);

}

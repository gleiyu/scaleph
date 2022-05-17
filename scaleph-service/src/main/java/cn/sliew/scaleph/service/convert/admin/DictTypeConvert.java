package cn.sliew.scaleph.service.convert.admin;

import cn.sliew.scaleph.dao.entity.master.system.DictType;
import cn.sliew.scaleph.service.convert.BaseConvert;
import cn.sliew.scaleph.service.dto.admin.DictTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictTypeConvert extends BaseConvert<DictType, DictTypeDTO> {
    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);
}
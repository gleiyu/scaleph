package cn.sliew.scaleph.api.controller.stdata;

import cn.sliew.scaleph.api.annotation.Logging;
import cn.sliew.scaleph.api.vo.ResponseVO;
import cn.sliew.scaleph.service.dto.meta.MetaDataMapDTO;
import cn.sliew.scaleph.service.dto.meta.MetaDataSetDTO;
import cn.sliew.scaleph.service.dto.meta.MetaDataSetTypeDTO;
import cn.sliew.scaleph.service.meta.MetaDataMapService;
import cn.sliew.scaleph.service.meta.MetaDataSetService;
import cn.sliew.scaleph.service.meta.MetaDataSetTypeService;
import cn.sliew.scaleph.service.param.meta.MetaDataMapParam;
import cn.sliew.scaleph.service.param.meta.MetaDataSetParam;
import cn.sliew.scaleph.service.param.meta.MetaDataSetTypeParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "数据标准-参考数据")
@RestController
@RequestMapping(path = "/api/stdata/ref")
public class RefDataController {

    @Autowired
    private MetaDataSetService metaDataSetService;
    @Autowired
    private MetaDataSetTypeService metaDataSetTypeService;
    @Autowired
    private MetaDataMapService metaDataMapService;

    @Logging
    @GetMapping(path = "/data")
    @ApiOperation(value = "分页查询参考数据", notes = "分页查询参考数据信息")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_SELECT)")
    public ResponseEntity<Page<MetaDataSetDTO>> listMetaDataSet(MetaDataSetParam param) {
        Page<MetaDataSetDTO> page = this.metaDataSetService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/data/type/{type}")
    @ApiOperation(value = "按类型查询参考数据", notes = "按类型查询参考数据")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_SELECT)")
    public ResponseEntity<List<MetaDataSetDTO>> listMetaDataByType(@PathVariable(value = "type") Long typeId) {
        List<MetaDataSetDTO> list = this.metaDataSetService.listByType(typeId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/data")
    @ApiOperation(value = "新增参考数据", notes = "新增参考数据")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_ADD)")
    public ResponseEntity<ResponseVO> addMetaDataSet(@Validated @RequestBody MetaDataSetDTO metaDataSetDTO) {
        this.metaDataSetService.insert(metaDataSetDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @PutMapping(path = "/data")
    @ApiOperation(value = "修改参考数据", notes = "修改参考数据")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_EDIT)")
    public ResponseEntity<ResponseVO> editMetaDataSet(@Validated @RequestBody MetaDataSetDTO metaDataSetDTO) {
        this.metaDataSetService.update(metaDataSetDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/data/{id}")
    @ApiOperation(value = "删除参考数据", notes = "删除参考数据")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_DELETE)")
    public ResponseEntity<ResponseVO> deleteMetaDataSet(@PathVariable(value = "id") String id) {
        this.metaDataSetService.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/data/batch")
    @ApiOperation(value = "批量删除参考数据", notes = "批量删除参考数据")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_DELETE)")
    public ResponseEntity<ResponseVO> deleteMetaDataSet(@RequestBody Map<Integer, String> map) {
        this.metaDataSetService.deleteBatch(map);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/type")
    @ApiOperation(value = "分页查询参考数据类型", notes = "分页查询参考数据类型信息")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_TYPE_SELECT)")
    public ResponseEntity<Page<MetaDataSetTypeDTO>> listMetaDataSetType(MetaDataSetTypeParam param) {
        Page<MetaDataSetTypeDTO> page = this.metaDataSetTypeService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/type")
    @ApiOperation(value = "新增参考数据类型", notes = "新增参考数据类型")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_TYPE_ADD)")
    public ResponseEntity<ResponseVO> addMetaDataSetType(@Validated @RequestBody MetaDataSetTypeDTO metaDataSetTypeDTO) {
        this.metaDataSetTypeService.insert(metaDataSetTypeDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @PutMapping(path = "/type")
    @ApiOperation(value = "修改参考数据类型", notes = "修改参考数据类型")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_TYPE_EDIT)")
    public ResponseEntity<ResponseVO> editMetaDataSetType(@Validated @RequestBody MetaDataSetTypeDTO metaDataSetTypeDTO) {
        this.metaDataSetTypeService.update(metaDataSetTypeDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/type/{id}")
    @ApiOperation(value = "删除参考数据类型", notes = "删除参考数据类型")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_TYPE_DELETE)")
    public ResponseEntity<ResponseVO> deleteMetaDataSetType(@PathVariable(value = "id") String id) {
        this.metaDataSetTypeService.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/type/batch")
    @ApiOperation(value = "批量删除参考数据类型", notes = "批量删除参考数据类型")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_TYPE_DELETE)")
    public ResponseEntity<ResponseVO> deleteMetaDataSetType(@RequestBody Map<Integer, String> map) {
        this.metaDataSetTypeService.deleteBatch(map);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/map")
    @ApiOperation(value = "分页查询参考数据映射", notes = "分页查询参考数据映射信息")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_MAP_SELECT)")
    public ResponseEntity<Page<MetaDataMapDTO>> listMetaDataMap(MetaDataMapParam param) {
        Page<MetaDataMapDTO> page = this.metaDataMapService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/map")
    @ApiOperation(value = "新增参考数据映射", notes = "新增参考数据映射")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_MAP_ADD)")
    public ResponseEntity<ResponseVO> addMetaDataMap(@Validated @RequestBody MetaDataMapDTO metaDataSetTypeDTO) {
        this.metaDataMapService.insert(metaDataSetTypeDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @PutMapping(path = "/map")
    @ApiOperation(value = "修改参考数据映射", notes = "修改参考数据映射")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_MAP_EDIT)")
    public ResponseEntity<ResponseVO> editMetaDataMap(@Validated @RequestBody MetaDataMapDTO metaDataSetTypeDTO) {
        this.metaDataMapService.update(metaDataSetTypeDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/map/{id}")
    @ApiOperation(value = "删除参考数据映射", notes = "删除参考数据映射")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_MAP_DELETE)")
    public ResponseEntity<ResponseVO> deleteMetaDataMap(@PathVariable(value = "id") String id) {
        this.metaDataMapService.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/map/batch")
    @ApiOperation(value = "批量删除参考数据映射", notes = "批量删除参考数据映射")
    @PreAuthorize("@svs.validate(T(cn.sliew.scaleph.common.constant.PrivilegeConstants).STDATA_REF_DATA_MAP_DELETE)")
    public ResponseEntity<ResponseVO> deleteMetaDataMap(@RequestBody Map<Integer, String> map) {
        this.metaDataMapService.deleteBatch(map);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }
}
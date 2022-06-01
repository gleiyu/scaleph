package cn.sliew.scaleph.dao.entity.master.system;

import cn.sliew.scaleph.dao.entity.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统配置信息表
 * </p>
 *
 * @author liyu
 * @since 2021-10-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
@ApiModel(value = "sysConfig对象", description = "系统配置信息表")
public class SysConfig extends BaseDO {

    private static final long serialVersionUID = -5437539010004884444L;

    @ApiModelProperty(value = "配置编码")
    private String cfgCode;

    @ApiModelProperty(value = "配置信息")
    private String cfgValue;


}
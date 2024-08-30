package string;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GlobalSellerPageVO  {

    private Long id;

    private Long userId;

    /**
     * 供应商名称
     */
    
    private String sellerName;
    /**
     * 联系人电话
     */
    
    private String contactTel;
    /**
     * 用户状态
     */
    
    private Integer userStatus;
    /**
     * '供货商类型：1.自营；2.第三方；3.区域代理；4.社区团购供应商,5.代销供应商,6.批发商'
     * Matata默认是2
     *
     * @see com.simba.store.user.common.constant.SellerType
     */
    private Integer sellerType;
    
    private String sellerTypeStr;
    /**
     * 商家类型
     *
     * @see com.simba.store.common.core.constant.PlatformType
     */
    private Integer systemType;

    private String systemTypeStr;
    
    private Long createUserId;
    
    private Long modifyUserId;
    
    private String createUsername;
    
    private String modifyUsername;

    private Date gmtCreate;
    
    private String loginAccount;
    /**
     * 角色列表
     */
    
    private List<RoleDetail> roles;
    @Data
    public static class RoleDetail {
        public String roleName;
        private Long roleId;
        private Long operatorId;
    }
    /**
     * 会员等级0普通1会员
     */
    
    private Integer memberLevel;

    /**
     * 会员过期时间
     */
    
    private Date memberExpireTime;

    
    private Integer registerSourceType;

    

    private Long sTimes;

    
    private Integer internalStaff;
}



package file;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ColumnFiled implements Serializable {

	private String msg;

	private LocalDateTime msgTime;

	private Long supplierId;

	private String supplierName;

	private String supplierTel;

	private Integer level;

	private boolean isInternal;

	private Long wholesalerId;

	private String wholesalerCountryCode;

	private String wholesalerName;

	private String wholesalerTel;

	private String wholesalerMarket;
}

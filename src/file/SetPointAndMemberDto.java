package file;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 设置会员和积分Dto
 */
@Data
public class SetPointAndMemberDto implements Serializable {
	private static final long serialVersionUID = 4473686397675696751L;

	private String phone;
	private Integer level;
	private Integer score;

	public static void main(String[] args) {
		List<SetPointAndMemberDto> list = new ArrayList<>();
		SetPointAndMemberDto dto = new SetPointAndMemberDto();
		dto.setPhone("13500010014");
		dto.setLevel(4);
		dto.setScore(1000);
		list.add(dto);

		SetPointAndMemberDto dto1 = new SetPointAndMemberDto();
		dto1.setPhone("13500010013");
		dto1.setLevel(4);
		dto1.setScore(2000);
		list.add(dto1);

		SetPointAndMemberDto dto2 = new SetPointAndMemberDto();
		dto2.setPhone("13500010012");
		dto2.setLevel(4);
		dto2.setScore(1500);
		list.add(dto2);

		SetPointAndMemberDto dto3 = new SetPointAndMemberDto();
		dto3.setPhone("13500010011");
		dto3.setLevel(4);
		dto3.setScore(1200);
		list.add(dto3);

		SetPointAndMemberDto dto4 = new SetPointAndMemberDto();
		dto4.setPhone("13500010010");
		dto4.setLevel(4);
		dto4.setScore(1800);
		list.add(dto4);

		SetPointAndMemberDto dto5 = new SetPointAndMemberDto();
		dto5.setPhone("15012232131");
		dto5.setLevel(4);
		dto5.setScore(1100);
		list.add(dto5);

		SetPointAndMemberDto dto6 = new SetPointAndMemberDto();
		dto6.setPhone("13500010009");
		dto6.setLevel(4);
		dto6.setScore(1600);
		list.add(dto6);
		String s = JSON.toJSONString(list);
		System.out.println(s);
	}
}

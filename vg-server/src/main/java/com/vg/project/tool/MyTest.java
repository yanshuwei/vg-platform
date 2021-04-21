package com.vg.project.tool;

import com.vg.common.utils.ImportExcelUtils;
import com.vg.common.utils.PinyinUtils;

import java.text.ParseException;

public class MyTest {
	/*public static void main(String[] args) {
		String input = "{\"userName\": \"admin\",\"password\":\"admin123\"}";
		String input2 = "NV7WzJ0tAhgco7eoJ6fSfcvARTTc+qQbwGTzj3Xga1ZoaseAgSrjRmZtKIsXwAZxWGnwFdFcGZaWOkVZCbpC2ooePrBi2TiyMk+v1UDfpDG29tiuwvcYK4HEfcHUfLv3K8nqo+b839A98yxBuYmHwKgH7ukJhxu/0NEZnr4RW0Q=";
		String serverEncode   = "{\n" +
				"    \"pageNo\":1,\n" +
				"    \"pageSize\":10\n" +
				"}";
		String clientDecode = "";
		try {
			//加密后的东西
			System.out.println("客户端加密密文：" + RSAUtil.clientEncrypt(serverEncode));
			//开始解密
			System.out.println("服务端解密明文：" + RSAUtil.serverDecrypt(input2));

			System.out.println("服务端加密：" + RSAUtil.serverEncrypt(serverEncode));

			System.out.println("客户端解密明文：" + RSAUtil.clientDecrypt(clientDecode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	public static void main(String[] args) throws ParseException {

	}

	/*void test(){
		try {
			File file = new File("D://test.xlsx");
			InputStream in = new FileInputStream(file);
			List<List<String>> res = new ImportExcelUtils().getBankListByExcel(in,file.getName());
			StringBuilder sb = new StringBuilder();
			for (List<String> dto : res) {
				String deptIds = dto.get(0);
				String name = dto.get(1);
				String phone = dto.get(2).substring(0,11);
				String email = dto.get(3);
				String userName = PinyinUtils.getHanziPinYin(name);
				User user = userService.selectUserByLoginName(userName);
				if(user == null){
					if(deptIds.contains("公司管理层")){
						continue;
					}
					user = new User();
					user.setUserType("Platform");
					user.setUserName(name);
					user.setLoginName(userName);
					user.setEmail(email);
					user.setPassword("123456");
					user.setPhonenumber(phone);
					user.setPostIds(new Long[]{4L});
					user.setRoleIds(new Long[]{120L});
					if(deptIds.contains(",")){
						Set<Long> deptList = new HashSet<>();
						String[] deptArr = deptIds.split(",");
						for (String s : deptArr) {
							String[] item = s.split("-");
							switch (item.length) {
								case 1 :
									Dept dept = new Dept();
									dept.setDeptName(item[0]);
									List<Dept> list = deptService.selectDeptList(dept);
									if(list != null && !list.isEmpty()){
										dept = list.get(0);
										deptList.add(dept.getDeptId());
									}else{
										sb.append("----" + item[0]+"\n");
									}
									break;
								case 2 :
									Dept dept2 = new Dept();
									dept2.setDeptName(item[0]);
									List<Dept> list2 = deptService.selectDeptList(dept2);
									if(list2 != null && !list2.isEmpty()){
										long deptId = list2.get(0).getDeptId();
										dept2.setParentId(deptId);
										dept2.setDeptName(item[1]);
										List<Dept> list22 = deptService.selectDeptList(dept2);
										if(list22 != null && !list22.isEmpty()){
											deptList.add(list22.get(0).getDeptId());
										}else{
											sb.append("case2----"+ item[0] + ":" + item[1]+"\n");
										}
									}else{
										sb.append("case2----" + item[0]+"\n");
									}
									break;
								case 3 :
									Dept dept3 = new Dept();
									dept3.setDeptName(item[0]);
									List<Dept> list3 = deptService.selectDeptList(dept3);
									if(list3 != null && !list3.isEmpty()){
										long deptId = list3.get(0).getDeptId();
										dept3.setParentId(deptId);
										dept3.setDeptName(item[1]);
										List<Dept> list33 = deptService.selectDeptList(dept3);
										if(list33 != null && !list33.isEmpty()){
											dept3.setParentId(list33.get(0).getDeptId());
											dept3.setDeptName(item[2]);
											List<Dept> list333 = deptService.selectDeptList(dept3);
											if(list333 != null && !list333.isEmpty()){
												deptList.add(list333.get(0).getDeptId());
											}else{
												sb.append("case3----" + item[0] +":"+ item[1]+":" + item[2]+"\n");
											}
										}else{
											sb.append("case3----" + item[0] +":" + item[1]+"\n");
										}
									}else{
										sb.append("case3----" + item[0]+"\n");
									}
									break;
							}
						}
						user.setDeptIds(deptList.toArray(new Long[deptList.size()]));
					}else{
						String[] item = deptIds.split("-");
						switch (item.length) {
							case 1 :
								Dept dept = new Dept();
								dept.setDeptName(item[0]);
								List<Dept> list = deptService.selectDeptList(dept);
								if(list != null && !list.isEmpty()){
									dept = list.get(0);
									user.setDeptIds(new Long[]{dept.getDeptId()});
								}else{
									sb.append("case1----" + item[0]+"\n");
								}
								break;
							case 2 :
								Dept dept2 = new Dept();
								dept2.setDeptName(item[0]);
								List<Dept> list2 = deptService.selectDeptList(dept2);
								if(list2 != null && !list2.isEmpty()){
									long deptId = list2.get(0).getDeptId();
									dept2.setParentId(deptId);
									dept2.setDeptName(item[1]);
									List<Dept> list22 = deptService.selectDeptList(dept2);
									if(list22 != null && !list22.isEmpty()){
										user.setDeptIds(new Long[]{list22.get(0).getDeptId()});
									}else{
										sb.append("case2----"+item[0] + ":" + item[1]+"\n");
									}
								}else{
									sb.append("case2----" + item[0]+"\n");
								}
								break;
							case 3 :
								Dept dept3 = new Dept();
								dept3.setDeptName(item[0]);
								List<Dept> list3 = deptService.selectDeptList(dept3);
								if(list3 != null && !list3.isEmpty()){
									long deptId = list3.get(0).getDeptId();
									dept3.setParentId(deptId);
									dept3.setDeptName(item[1]);
									List<Dept> list33 = deptService.selectDeptList(dept3);
									if(list33 != null && !list33.isEmpty()){
										dept3.setParentId(list33.get(0).getDeptId());
										dept3.setDeptName(item[2]);
										List<Dept> list333 = deptService.selectDeptList(dept3);
										if(list333 != null && !list333.isEmpty()){
											user.setDeptIds(new Long[]{list333.get(0).getDeptId()});
										}else{
											sb.append("case3----" + item[0] + ":" + item[1] + ":" + item[2]+"\n");
										}
									}else{
										sb.append("case3----" + item[0] + ":" + item[1]+"\n");
									}
								}else{
									sb.append("case3----" + item[0]+"\n");
								}
								break;
						}
					}
					userService.insertUser(user);
				}else{
					user.setRoleIds(new Long[]{120L});
					userService.updateUserRole(user);
				}
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}

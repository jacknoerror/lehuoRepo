package com.lehuo.util;

import java.util.Date;

import android.content.Context;


public class HandleStrings {
	/*public static void main(String[] args) {
//		quote();
//		res();
//		givitafinal();
//		givitajson();
//		nameit();
//		convertprivateStringNAmeIntoJson();
//		readAndConvert();
		readAndConvert3();
	}*/
	private static void readAndConvert(){
		String content = ChangeNameIO.readFile("/Users/taotao/log.txt");
		
		String deleted = deleteBetweenPrivateAndSemi(content);
		
		convertprivateStringNAmeIntoJson(deleted);
	}
	public static String readAndConvert3(Context c){
		String content = ChangeNameIO.readFromSomeWhere(c);
		String replace = content.replace(";", ";\n");
		String convert = convertprivateStringNAmeIntoJson(replace);
		StringBuffer sb = new StringBuffer();
		sb.append(replace).append("\n").append(convert);
		return sb.toString();
	}
	private static String deleteBetweenPrivateAndSemi(String content){
		int from=0,to=1;
		do{
			from = content.indexOf(";",to);
			if(from<0)break;
			to = content.indexOf("private",from);
			String sub="";
			if(to>0){
				sub = content.substring(from, to);
			}else{
				sub = content.substring(from);
			}
			content=content.replace(sub, ":");
//			System.out.println(sub);
		}while(from>=0);
		return content.replace(":", ";")	;
	}

	private  static String  convertprivateStringNAmeIntoJson(final String  input) {
//		final String  input = "private int addressId;private int memberId;private String addressName;private String phone;private String zipcode;private String contact;private boolean defaultSign ;";
		StringBuffer sb = new StringBuffer();
		for(String s1:input.split(";"))	{
			if(s1.length()<=2) break;//字符串过短
			String[] s2 = s1.split(" ");
			String out = String.format("if(job.has(\"%s\")) %s = job.get%s(\"%s\");", s2[2],s2[2],s2[1],s2[2]);
//			String out = "if(job.has(\""+s2[2]+"\")) set"+firstBig(s2[2])+"(job.get"+firstBig(s2[1])+"(\""+s2[2]+"\"));";
			sb.append(out.replace("Integer", "Int")).append("\n");
		}
		return sb.toString();
	}
	private static String firstBig(String s){
		if(s.isEmpty())return s;
		return s.toUpperCase().substring(0, 1)+s.substring(1);
	}

	private static void nameit() {
		final String input = "com.android.zjboc.cj.bank.ScoreLogService.websvcFindcom.android.zjboc.cj.gift.ScoreLogService.sendSmscom.android.zjboc.cj.gift.GiftConfigService.getcom.android.zjboc.cj.gift.GiftExchangeInfoService.websvcFindPagecom.android.zjboc.cj.gift.GiftExchangeInfoService.getExchangeManyInfocom.android.zjboc.cj.gift.GiftExchangeInfoService.websvcGetcom.android.zjboc.cj.gift.GiftExchangeInfoService.newExchangeGiftcom.android.zjboc.cj.gift.PrizeInfoService.giftcom.android.zjboc.cj.gift.PrizeInfoService.websvcFindcom.android.zjboc.cj.gift.PublicGiftService.getExchangeListcom.android.zjboc.cj.gift.PublicGiftService.getGiftListcom.android.zjboc.cj.member.AddressInfoService.websvcGetcom.android.zjboc.cj.member.AddressInfoService.savecom.android.zjboc.cj.member.AddressInfoService.deletecom.android.zjboc.cj.member.AddressInfoService.setDefaultcom.android.zjboc.cj.member.MemberInfoService.websvcGetMembercom.android.zjboc.cj.member.MemberInfoService.addMembercom.android.zjboc.cj.member.MemberInfoService.saveMembercom.android.zjboc.cj.member.MemberInfoService.websvcFindUniqueMemberInfoByNamecom.android.zjboc.cj.member.MemberInfoService.websvcFindUniqueMemberInfoByCardNocom.android.zjboc.cj.member.MemberInfoService.scanScoreLogcom.android.zjboc.cj.member.MemberInfoService.getMobileNoByMemberInfocom.android.zjboc.cj.member.MemberInfoService.createLoginHistorycom.android.zjboc.cj.member.MemberInfoService.logoutcom.android.zjboc.cj.member.ScoreInfoService.getScoreLeftNumByMemberId";
//		String str = input.replace("com.android.zjboc.cj.", ";public final String REQUEST_").replace(".", "_").toUpperCase().replace(";",";\n");
		for(String s :input.replace("com.", ";com.").split(";")){
			System.out.println("public static final String "+s.replace("com.android.zjboc.cj.", "REQUEST_").replace(".", "_").toUpperCase()
					+" = \""+s+"\";");
		}
//		System.out.println(str);
		
	}

	private static void res() {
		// TODO Auto-generated method stub
		
	}

	private static void quote() {
		final String input = "我的商铺，我的收藏，我的人脉，同步我的商铺，离线模式，清空本地数据，移动商铺介绍，移动商铺企业推荐";
		final String divider = "，";
		
		for(String s:input.split(divider)){
			System.out.print("\""+s+"\",");
			
		}
	}
	
	private static void givitafinal(){
		final String input = "shopId,shopName,service,compIntro,hasMotion,mainProducts,shopLogoImage";
		String PFS="public final String ";
		String divider=",";
		String prefix="COMPANY_";
		
		for(String s:input.split(divider)){
			System.out.println(PFS+prefix+s.trim().toUpperCase()+" =\""+s.trim()+"\";");
		}
	}
	private static void givitajson(){
		/*example*/
		/*if(job.has(PRODUCT_PRODUCTID))pp.setProductId(job.getInt(PRODUCT_PRODUCTID));*/
		final String input = "shopId,shopName,service,compIntro,hasMotion,mainProducts,shopLogoImage";
		
		for(String s:input.split(",")){
//			System.out.println("if(job.has(COMPANY_"+s.toUpperCase()+"))pp.set"+s.toUpperCase()+"( = job.get"+s+"("+s.toUpperCase().toUpperCase()+");");
			System.out.println("if(job.has("+s.toUpperCase()+"))pp.set"+s+"(job.getXXX("+s.toUpperCase()+"));");
		}
	}
}

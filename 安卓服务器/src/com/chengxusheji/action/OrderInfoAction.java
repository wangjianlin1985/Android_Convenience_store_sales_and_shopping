package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.OrderInfoDAO;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.PayWayDAO;
import com.chengxusheji.domain.PayWay;
import com.chengxusheji.dao.SendWayDAO;
import com.chengxusheji.domain.SendWay;
import com.chengxusheji.dao.SellerDAO;
import com.chengxusheji.domain.Seller;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: �������*/
    private String orderNo;
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderNo() {
        return this.orderNo;
    }

    /*�������Ҫ��ѯ������: �µ��û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ֧����ʽ*/
    private PayWay payWay;
    public void setPayWay(PayWay payWay) {
        this.payWay = payWay;
    }
    public PayWay getPayWay() {
        return this.payWay;
    }

    /*�������Ҫ��ѯ������: ����״̬*/
    private String orderStateObj;
    public void setOrderStateObj(String orderStateObj) {
        this.orderStateObj = orderStateObj;
    }
    public String getOrderStateObj() {
        return this.orderStateObj;
    }

    /*�������Ҫ��ѯ������: �µ�ʱ��*/
    private String orderTime;
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getOrderTime() {
        return this.orderTime;
    }

    /*�������Ҫ��ѯ������: �ջ���*/
    private String receiveName;
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }
    public String getReceiveName() {
        return this.receiveName;
    }

    /*�������Ҫ��ѯ������: �ջ��˵绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*�������Ҫ��ѯ������: �ͻ���ʽ*/
    private SendWay sendWayObj;
    public void setSendWayObj(SendWay sendWayObj) {
        this.sendWayObj = sendWayObj;
    }
    public SendWay getSendWayObj() {
        return this.sendWayObj;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private Seller sellerObj;
    public void setSellerObj(Seller sellerObj) {
        this.sellerObj = sellerObj;
    }
    public Seller getSellerObj() {
        return this.sellerObj;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource UserInfoDAO userInfoDAO;
    @Resource PayWayDAO payWayDAO;
    @Resource SendWayDAO sendWayDAO;
    @Resource SellerDAO sellerDAO;
    @Resource OrderInfoDAO orderInfoDAO;

    /*��������OrderInfo����*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }

    /*��ת�����OrderInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*��ѯ���е�PayWay��Ϣ*/
        List<PayWay> payWayList = payWayDAO.QueryAllPayWayInfo();
        ctx.put("payWayList", payWayList);
        /*��ѯ���е�SendWay��Ϣ*/
        List<SendWay> sendWayList = sendWayDAO.QueryAllSendWayInfo();
        ctx.put("sendWayList", sendWayList);
        /*��ѯ���е�Seller��Ϣ*/
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        return "add_view";
    }

    /*���OrderInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��������Ƿ��Ѿ�����*/
        String orderNo = orderInfo.getOrderNo();
        OrderInfo db_orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);
        if(null != db_orderInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�ö�������Ѿ�����!"));
            return "error";
        }
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(orderInfo.getUserObj().getUser_name());
            orderInfo.setUserObj(userObj);
            PayWay payWay = payWayDAO.GetPayWayByPayWayId(orderInfo.getPayWay().getPayWayId());
            orderInfo.setPayWay(payWay);
            SendWay sendWayObj = sendWayDAO.GetSendWayBySendWayId(orderInfo.getSendWayObj().getSendWayId());
            orderInfo.setSendWayObj(sendWayObj);
            Seller sellerObj = sellerDAO.GetSellerBySellUserName(orderInfo.getSellerObj().getSellUserName());
            orderInfo.setSellerObj(sellerObj);
            orderInfoDAO.AddOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderInfo��Ϣ*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderStateObj == null) orderStateObj = "";
        if(orderTime == null) orderTime = "";
        if(receiveName == null) receiveName = "";
        if(telephone == null) telephone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, userObj, payWay, orderStateObj, orderTime, receiveName, telephone, sendWayObj, sellerObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, userObj, payWay, orderStateObj, orderTime, receiveName, telephone, sendWayObj, sellerObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderNo", orderNo);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("payWay", payWay);
        List<PayWay> payWayList = payWayDAO.QueryAllPayWayInfo();
        ctx.put("payWayList", payWayList);
        ctx.put("orderStateObj", orderStateObj);
        ctx.put("orderTime", orderTime);
        ctx.put("receiveName", receiveName);
        ctx.put("telephone", telephone);
        ctx.put("sendWayObj", sendWayObj);
        List<SendWay> sendWayList = sendWayDAO.QueryAllSendWayInfo();
        ctx.put("sendWayList", sendWayList);
        ctx.put("sellerObj", sellerObj);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryOrderInfoOutputToExcel() { 
        if(orderNo == null) orderNo = "";
        if(orderStateObj == null) orderStateObj = "";
        if(orderTime == null) orderTime = "";
        if(receiveName == null) receiveName = "";
        if(telephone == null) telephone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo,userObj,payWay,orderStateObj,orderTime,receiveName,telephone,sendWayObj,sellerObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderInfo��Ϣ��¼"; 
        String[] headers = { "�������","�µ��û�","�����ܽ��","֧����ʽ","����״̬","�µ�ʱ��","�ջ���","�ջ��˵绰","�ͻ���ʽ","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderInfoList.size();i++) {
        	OrderInfo orderInfo = orderInfoList.get(i); 
        	dataset.add(new String[]{orderInfo.getOrderNo(),orderInfo.getUserObj().getName(),
orderInfo.getTotalMoney() + "",orderInfo.getPayWay().getPayWayName(),
orderInfo.getOrderStateObj(),orderInfo.getOrderTime(),orderInfo.getReceiveName(),orderInfo.getTelephone(),orderInfo.getSendWayObj().getSendWayName(),
orderInfo.getSellerObj().getSellerName()
});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"OrderInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯOrderInfo��Ϣ*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderNo == null) orderNo = "";
        if(orderStateObj == null) orderStateObj = "";
        if(orderTime == null) orderTime = "";
        if(receiveName == null) receiveName = "";
        if(telephone == null) telephone = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(orderNo, userObj, payWay, orderStateObj, orderTime, receiveName, telephone, sendWayObj, sellerObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(orderNo, userObj, payWay, orderStateObj, orderTime, receiveName, telephone, sendWayObj, sellerObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderNo", orderNo);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("payWay", payWay);
        List<PayWay> payWayList = payWayDAO.QueryAllPayWayInfo();
        ctx.put("payWayList", payWayList);
        ctx.put("orderStateObj", orderStateObj);
        ctx.put("orderTime", orderTime);
        ctx.put("receiveName", receiveName);
        ctx.put("telephone", telephone);
        ctx.put("sendWayObj", sendWayObj);
        List<SendWay> sendWayList = sendWayDAO.QueryAllSendWayInfo();
        ctx.put("sendWayList", sendWayList);
        ctx.put("sellerObj", sellerObj);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderNo��ȡOrderInfo����*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<PayWay> payWayList = payWayDAO.QueryAllPayWayInfo();
        ctx.put("payWayList", payWayList);
        List<SendWay> sendWayList = sendWayDAO.QueryAllSendWayInfo();
        ctx.put("sendWayList", sendWayList);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        ctx.put("orderInfo",  orderInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderNo��ȡOrderInfo����*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderNo(orderNo);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<PayWay> payWayList = payWayDAO.QueryAllPayWayInfo();
        ctx.put("payWayList", payWayList);
        List<SendWay> sendWayList = sendWayDAO.QueryAllSendWayInfo();
        ctx.put("sendWayList", sendWayList);
        List<Seller> sellerList = sellerDAO.QueryAllSellerInfo();
        ctx.put("sellerList", sellerList);
        ctx.put("orderInfo",  orderInfo);
        return "front_show_view";
    }

    /*�����޸�OrderInfo��Ϣ*/
    public String ModifyOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(orderInfo.getUserObj().getUser_name());
            orderInfo.setUserObj(userObj);
            PayWay payWay = payWayDAO.GetPayWayByPayWayId(orderInfo.getPayWay().getPayWayId());
            orderInfo.setPayWay(payWay);
            SendWay sendWayObj = sendWayDAO.GetSendWayBySendWayId(orderInfo.getSendWayObj().getSendWayId());
            orderInfo.setSendWayObj(sendWayObj);
            Seller sellerObj = sellerDAO.GetSellerBySellUserName(orderInfo.getSellerObj().getSellUserName());
            orderInfo.setSellerObj(sellerObj);
            orderInfoDAO.UpdateOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderInfo��Ϣ*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderNo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfoɾ��ʧ��!"));
            return "error";
        }
    }

}

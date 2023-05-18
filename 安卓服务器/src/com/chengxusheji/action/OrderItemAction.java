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
import com.chengxusheji.dao.OrderItemDAO;
import com.chengxusheji.domain.OrderItem;
import com.chengxusheji.dao.OrderInfoDAO;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderItemAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��������*/
    private OrderInfo orderObj;
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }
    public OrderInfo getOrderObj() {
        return this.orderObj;
    }

    /*�������Ҫ��ѯ������: ������Ʒ*/
    private Product productObj;
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }
    public Product getProductObj() {
        return this.productObj;
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

    private int itemId;
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getItemId() {
        return itemId;
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
    @Resource OrderInfoDAO orderInfoDAO;
    @Resource ProductDAO productDAO;
    @Resource OrderItemDAO orderItemDAO;

    /*��������OrderItem����*/
    private OrderItem orderItem;
    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
    public OrderItem getOrderItem() {
        return this.orderItem;
    }

    /*��ת�����OrderItem��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�OrderInfo��Ϣ*/
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        /*��ѯ���е�Product��Ϣ*/
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        return "add_view";
    }

    /*���OrderItem��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddOrderItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderItem.getOrderObj().getOrderNo());
            orderItem.setOrderObj(orderObj);
            Product productObj = productDAO.GetProductByProductId(orderItem.getProductObj().getProductId());
            orderItem.setProductObj(productObj);
            orderItemDAO.AddOrderItem(orderItem);
            ctx.put("message",  java.net.URLEncoder.encode("OrderItem��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderItem���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderItem��Ϣ*/
    public String QueryOrderItem() {
        if(currentPage == 0) currentPage = 1;
        List<OrderItem> orderItemList = orderItemDAO.QueryOrderItemInfo(orderObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderItemDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderItemDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderItemList",  orderItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderObj", orderObj);
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryOrderItemOutputToExcel() { 
        List<OrderItem> orderItemList = orderItemDAO.QueryOrderItemInfo(orderObj,productObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderItem��Ϣ��¼"; 
        String[] headers = { "��Ŀid","��������","������Ʒ","��Ʒ����","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderItemList.size();i++) {
        	OrderItem orderItem = orderItemList.get(i); 
        	dataset.add(new String[]{orderItem.getItemId() + "",orderItem.getOrderObj().getOrderNo(),
orderItem.getProductObj().getProductName(),
orderItem.getPrice() + "",orderItem.getOrderNumer() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"OrderItem.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯOrderItem��Ϣ*/
    public String FrontQueryOrderItem() {
        if(currentPage == 0) currentPage = 1;
        List<OrderItem> orderItemList = orderItemDAO.QueryOrderItemInfo(orderObj, productObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderItemDAO.CalculateTotalPageAndRecordNumber(orderObj, productObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderItemDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = orderItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderItemList",  orderItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("orderObj", orderObj);
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�OrderItem��Ϣ*/
    public String ModifyOrderItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������itemId��ȡOrderItem����*/
        OrderItem orderItem = orderItemDAO.GetOrderItemByItemId(itemId);

        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("orderItem",  orderItem);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�OrderItem��Ϣ*/
    public String FrontShowOrderItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������itemId��ȡOrderItem����*/
        OrderItem orderItem = orderItemDAO.GetOrderItemByItemId(itemId);

        List<OrderInfo> orderInfoList = orderInfoDAO.QueryAllOrderInfoInfo();
        ctx.put("orderInfoList", orderInfoList);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("orderItem",  orderItem);
        return "front_show_view";
    }

    /*�����޸�OrderItem��Ϣ*/
    public String ModifyOrderItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            OrderInfo orderObj = orderInfoDAO.GetOrderInfoByOrderNo(orderItem.getOrderObj().getOrderNo());
            orderItem.setOrderObj(orderObj);
            Product productObj = productDAO.GetProductByProductId(orderItem.getProductObj().getProductId());
            orderItem.setProductObj(productObj);
            orderItemDAO.UpdateOrderItem(orderItem);
            ctx.put("message",  java.net.URLEncoder.encode("OrderItem��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderItem��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderItem��Ϣ*/
    public String DeleteOrderItem() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderItemDAO.DeleteOrderItem(itemId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderItemɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderItemɾ��ʧ��!"));
            return "error";
        }
    }

}

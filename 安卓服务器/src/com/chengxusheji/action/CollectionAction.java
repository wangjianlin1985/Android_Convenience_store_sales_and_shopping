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
import com.chengxusheji.dao.CollectionDAO;
import com.chengxusheji.domain.Collection;
import com.chengxusheji.dao.ProductDAO;
import com.chengxusheji.domain.Product;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CollectionAction extends BaseAction {

    /*�������Ҫ��ѯ������: �ղر���*/
    private Product productObj;
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }
    public Product getProductObj() {
        return this.productObj;
    }

    /*�������Ҫ��ѯ������: �ղ��û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: �ղ�ʱ��*/
    private String collectionTime;
    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }
    public String getCollectionTime() {
        return this.collectionTime;
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

    private int collectionId;
    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }
    public int getCollectionId() {
        return collectionId;
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
    @Resource ProductDAO productDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource CollectionDAO collectionDAO;

    /*��������Collection����*/
    private Collection collection;
    public void setCollection(Collection collection) {
        this.collection = collection;
    }
    public Collection getCollection() {
        return this.collection;
    }

    /*��ת�����Collection��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Product��Ϣ*/
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Collection��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCollection() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Product productObj = productDAO.GetProductByProductId(collection.getProductObj().getProductId());
            collection.setProductObj(productObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(collection.getUserObj().getUser_name());
            collection.setUserObj(userObj);
            collectionDAO.AddCollection(collection);
            ctx.put("message",  java.net.URLEncoder.encode("Collection��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Collection���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCollection��Ϣ*/
    public String QueryCollection() {
        if(currentPage == 0) currentPage = 1;
        if(collectionTime == null) collectionTime = "";
        List<Collection> collectionList = collectionDAO.QueryCollectionInfo(productObj, userObj, collectionTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        collectionDAO.CalculateTotalPageAndRecordNumber(productObj, userObj, collectionTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = collectionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = collectionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("collectionList",  collectionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("collectionTime", collectionTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCollectionOutputToExcel() { 
        if(collectionTime == null) collectionTime = "";
        List<Collection> collectionList = collectionDAO.QueryCollectionInfo(productObj,userObj,collectionTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Collection��Ϣ��¼"; 
        String[] headers = { "�ղ�id","�ղر���","�ղ��û�","�ղ�ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<collectionList.size();i++) {
        	Collection collection = collectionList.get(i); 
        	dataset.add(new String[]{collection.getCollectionId() + "",collection.getProductObj().getProductName(),
collection.getUserObj().getName(),
collection.getCollectionTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Collection.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯCollection��Ϣ*/
    public String FrontQueryCollection() {
        if(currentPage == 0) currentPage = 1;
        if(collectionTime == null) collectionTime = "";
        List<Collection> collectionList = collectionDAO.QueryCollectionInfo(productObj, userObj, collectionTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        collectionDAO.CalculateTotalPageAndRecordNumber(productObj, userObj, collectionTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = collectionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = collectionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("collectionList",  collectionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("productObj", productObj);
        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("collectionTime", collectionTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Collection��Ϣ*/
    public String ModifyCollectionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������collectionId��ȡCollection����*/
        Collection collection = collectionDAO.GetCollectionByCollectionId(collectionId);

        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("collection",  collection);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Collection��Ϣ*/
    public String FrontShowCollectionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������collectionId��ȡCollection����*/
        Collection collection = collectionDAO.GetCollectionByCollectionId(collectionId);

        List<Product> productList = productDAO.QueryAllProductInfo();
        ctx.put("productList", productList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("collection",  collection);
        return "front_show_view";
    }

    /*�����޸�Collection��Ϣ*/
    public String ModifyCollection() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Product productObj = productDAO.GetProductByProductId(collection.getProductObj().getProductId());
            collection.setProductObj(productObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(collection.getUserObj().getUser_name());
            collection.setUserObj(userObj);
            collectionDAO.UpdateCollection(collection);
            ctx.put("message",  java.net.URLEncoder.encode("Collection��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Collection��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Collection��Ϣ*/
    public String DeleteCollection() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            collectionDAO.DeleteCollection(collectionId);
            ctx.put("message",  java.net.URLEncoder.encode("Collectionɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Collectionɾ��ʧ��!"));
            return "error";
        }
    }

}

package cn.itcast.bookstore.book.web.servlet.admin;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.bookstore.book.domin.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domin.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;

@WebServlet("/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		/*
		 * �����ܵı����ݴ�С50kb
		 * d:\tempt Ϊ�����ļ�
		 */
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		// ���õ����ļ���СΪ15KB
		sfu.setFileSizeMax(20 * 1024);
		
		/*
		 * �õ����з���
		 */
		
		List<Category> category_list = categoryService.findAll();
		request.setAttribute("category_list", category_list);
		try {
			List<FileItem> list = sfu.parseRequest(request);
			Map<String,String> map = new HashMap<String,String>();
			for(FileItem fileItem:list)
			{
				//�����ݴ���map
				if(fileItem.isFormField())
				{
					map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
					
				}
				
			}
			
			/*
			 * ��װBook
			 * 
			 */
			Book book = CommonUtils.toBean(map, Book.class);
			Category category = CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			book.setBid(CommonUtils.uuid());
			
		
			/*
			 * ����ͼƬ
			 * ��ȡͼƬ��·��
			 * ��ȫbook ��image
			 * 
			 */
			
			String path = request.getServletContext().getRealPath("/book_img");
			FileItem fileItem = list.get(1);
			String fileName = fileItem.getName();
			
			//�ж�fileNameʱ���·�����Ǿ���·��
			if(fileName.contains("\\"))
			{
				int index = fileName.lastIndexOf("\\");
				fileName = fileName.substring(index+1);
			}
			//Ϊ�ļ�������id ��ֹ�ļ����ظ�
			fileName = CommonUtils.uuid() + "_" + fileName;
		
			File file = new File(path,fileName);

			fileItem.write(file);
			
			
			/*
			 * У��ͼƬ�Ĵ�С
			 */
			Image img = new ImageIcon(file.getAbsolutePath()).getImage();
			if(img.getWidth(null) > 200 || img.getHeight(null) > 200)
			{
				
				//ɾ��ͼƬ
				file.delete();
				request.setAttribute("book", book);
				request.setAttribute("msg", "ͼƬ�ĳߴ����200!");
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
				return;
			}
					
			/*
			 * ����book��image����·�� 
			 * ��ʽΪbook_img/...
			 */
			book.setImage("book_img/" + fileName);
			
			bookService.addBook(book);
			
			request.getRequestDispatcher("/admin/AdminBookServlet?method=findAllBook").forward(request, response);
			
		} catch (Exception e) {
			if(e instanceof FileUploadBase.FileSizeLimitExceededException)
			{
				request.setAttribute("msg", "���ϴ����ļ�������50KB");
				request.setAttribute("category_list",category_list);
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
			}
		} 
		
		
	}

	
	
	
	
	
	
	
	
	
}

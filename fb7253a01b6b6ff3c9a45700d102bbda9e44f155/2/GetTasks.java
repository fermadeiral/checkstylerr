package ru.szhernovoy.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.hibernate.Session;
import ru.szhernovoy.model.Item;
import ru.szhernovoy.service.HibernateSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by dort on 02.01.17.
 */
public class GetTasks extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     /*   resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");

        boolean alltasks = Boolean.valueOf(req.getParameter("done"));
        PrintWriter out = new PrintWriter(resp.getOutputStream());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tasks",getTasks(alltasks).toString());*/
    }
/*
    public JsonArray getTasks(boolean all){

        JsonArray array = new JsonArray();

        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        String query = null;
        if(all){
            query = "from ru.szhernovoy.model.Item";
        }else{
            query = "from ru.szhernovoy.model.Item as i where i.done = false";
        }

        Collection<Item> tasks = session.createQuery(query).list();
        session.getTransaction().commit();
        session.close();
        Item item = null;
        for (Item task : tasks){
            JsonObject obj = new JsonObject();
            obj.addProperty("descr",task.getDesc());
            obj.addProperty("create",task.getCreate().toString());
            obj.addProperty("done",task.getDone());
            array.add(obj);
        }
        return array;
    }
*/
}


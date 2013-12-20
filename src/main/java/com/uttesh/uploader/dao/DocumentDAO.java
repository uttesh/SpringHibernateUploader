package com.uttesh.uploader.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import com.uttesh.uploader.model.Document;
import org.hibernate.Hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DocumentDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    

    @Transactional
    public void save(Document document, InputStream inputStream) throws IOException {
        System.out.println("in save method");
        Session session = sessionFactory.getCurrentSession();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
       // Blob blob = Hibernate.getLobCreator(session).createBlob(buffer.toByteArray());
        document.setContent(buffer.toByteArray());
        session.save(document);
    }

    @Transactional
    public List<Document> list() {
        Session session = sessionFactory.getCurrentSession();
        List<Document> documents = null;
        try {
            documents = (List<Document>) session.createQuery("from Document").list();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return documents;
    }

    @Transactional
    public Document get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return (Document) session.get(Document.class, id);
    }

    @Transactional
    public void remove(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        Document document = (Document) session.get(Document.class, id);

        session.delete(document);
    }
}

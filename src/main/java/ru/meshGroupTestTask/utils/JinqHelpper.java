package ru.meshGroupTestTask.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class JinqHelpper {

    @PersistenceContext
    private final EntityManager em;

    public <U> JPAJinqStream<U> streamAll(Class<U> entity) {
        JinqJPAStreamProvider streams = new JinqJPAStreamProvider(em.getMetamodel());

        return ( streams.streamAll(em, entity)
                .setHint("queryLogger", LoggerFactory.getLogger("org.hibernate.SQL"))
                .setHint("exceptionOnTranslationFail", true));
    }
}
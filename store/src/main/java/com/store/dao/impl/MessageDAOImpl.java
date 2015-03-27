package com.store.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.store.dao.MessageDAO;
import com.store.entity.Message;

@Transactional
@Component("messageDAO")
public class MessageDAOImpl extends StoreDAOImpl<Message> implements MessageDAO {

	
}

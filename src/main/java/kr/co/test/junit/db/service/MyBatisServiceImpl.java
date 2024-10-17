package kr.co.test.junit.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import common.LogDeclare;
import common.spring.resolver.ParamCollector;
import common.util.map.ResultSetMap;
import kr.co.test.junit.db.mapper.MemberMapper;

@Service
public class MyBatisServiceImpl extends LogDeclare implements MyBatisService {
	
	@Autowired
	private MemberMapper userMapper;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public List<ResultSetMap> listUser() {
		return userMapper.selectAll();
	}

	@Override
	public void addUser(ParamCollector paramCollector) {
		userMapper.insert(paramCollector.getMapAll());
	}

	@Override
	public ResultSetMap getUser(ParamCollector paramCollector) {
		return userMapper.selectOne(paramCollector.getMapAll());
	}

	@Override
	public void modifyUser(ParamCollector paramCollector) {
		userMapper.update(paramCollector.getMapAll());
	}

	@Override
	public void removeUser(ParamCollector paramCollector) {
		userMapper.delete(paramCollector.getMapAll());
	}

	@Override
	public int manualTransaction1(ParamCollector paramCollector) {
		try {
			userMapper.delete(paramCollector.getMapAll());
			userMapper.insert(paramCollector.getMapAll());
			
			return 200;
			
		} catch (Exception e) {
			logger.error("", e);
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 500;
		}
	}

	@Override
	public int manualTransaction2(ParamCollector paramCollector) {
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		
		try {
			userMapper.delete(paramCollector.getMapAll());
			userMapper.insert(paramCollector.getMapAll());
			
			transactionManager.commit(transactionStatus);
			return 200;
			
		} catch (Exception e) {
			logger.error("", e);
			
			transactionManager.rollback(transactionStatus);
			return 500;
		}
	}
	
}

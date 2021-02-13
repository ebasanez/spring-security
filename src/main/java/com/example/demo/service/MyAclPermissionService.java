package com.example.demo.service;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.RequiredArgsConstructor;

/**
 * 11.3.2
 * @author ebasanez
 * @since 2021-02-13
 */
@Service
@RequiredArgsConstructor
public class MyAclPermissionService {

	private final MutableAclService aclService;
	private final PlatformTransactionManager transactionManager;

	public void addPermissionForUser(IEntity targetObj, Permission permission, String username) {
		final Sid sid = new PrincipalSid(username);
		addPermissionForSid(targetObj, permission, sid);
	}

	private void addPermissionForSid(IEntity targetObj, Permission permission, Sid sid) {
		// Explicitly create this transaction template to create acl entry inside the same transaction
		final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				final ObjectIdentity oi = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());
				MutableAcl acl = null;
				// Search if acl entry exists and inset if it does not
				try {
					acl = (MutableAcl) aclService.readAclById(oi);

				} catch (final NotFoundException e) {
					acl = aclService.createAcl(oi);
				}
				acl.insertAce(acl.getEntries().size(), permission, sid, true);
				aclService.updateAcl(acl);
			}
		});

	}

}

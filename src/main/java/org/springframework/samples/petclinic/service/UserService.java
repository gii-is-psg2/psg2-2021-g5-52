/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	private final UserRepository userRepository;


	@Autowired
	public UserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveUser(final User user) throws DataAccessException {
		user.setEnabled(true);
		this.userRepository.save(user);
	}

	@Transactional
	public boolean userHaveRol(final String username, final String rol) {
		boolean res = false;
		final Optional<User> user = this.findUser(username);
		if (user.isPresent()) {
			final Set<Authorities> roles = user.get().getAuthorities();
			for (final Authorities a : roles) {
				if (a.getAuthority().equals(rol)) {
					res = true;
					break;
				}
			}
		}
		return res;
	}
	public Optional<User> findUser(final String username) {
		return this.userRepository.findById(username);
	}

	public User getUserSession() {
		User usuario = new User();
		final Optional<User> user = this.findUser(SecurityContextHolder.getContext().getAuthentication().getName());
		if (user.isPresent()) {
			usuario = user.get();
		}
		return usuario;
	}

	@Transactional
	public void deleteUser(final User user) throws DataAccessException {
		this.userRepository.delete(user);
	}

}

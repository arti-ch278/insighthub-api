package com.artichourey.insighthub.serviceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.artichourey.insighthub.dtos.UserRequestDto;
import com.artichourey.insighthub.dtos.UserResponseDto;
import com.artichourey.insighthub.entities.User;
import com.artichourey.insighthub.exception.ResourceNotFoundException;
import com.artichourey.insighthub.mapper.UserMapper;
import com.artichourey.insighthub.repositories.UserRepository;
import com.artichourey.insighthub.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class UserServiceImpl implements UserService {
 
	private final UserMapper userMapper;
	
	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserResponseDto createUser(UserRequestDto dto) {
		 log.info("Creating new user with email: {}", dto.getEmail());
	      User user=userMapper.toEntity(dto);
	      user.setId(null);
	      user.setPassword(passwordEncoder.encode(dto.getPassword()));
	     User savedUser= userRepository.save(user);
	     log.info("User created successfully with id: {}", savedUser.getId());
	     return userMapper.toDto(savedUser);
	  
	}

	@Override
	public UserResponseDto updateUser(UserRequestDto dto,Long id ) {
		log.info("Updating user with id: {}", id);
		User user = getUserOrThrow(id);
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setAbout(dto.getAbout());
		user.setPassword(dto.getPassword());
		 User savedUser=userRepository.save(user);
		 log.info("User updated successfully with id: {}", id);
		return userMapper.toDto(savedUser);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserResponseDto> getAllUser(int page, int size) {
		log.info("Fetching users - page: {}, size: {}", page, size);

	    Pageable pageable = PageRequest.of(
	            page,
	            size,
	            Sort.by("id"));

	    Page<User> userPage = userRepository.findAll(pageable);

	    log.info("Total users found: {}", userPage.getTotalElements());

	    return userPage.map(userMapper::toDto);
		
	}

	@Override
	public UserResponseDto getUserById(Long id) {
		log.info("Fetching user with id: {}", id);
		User user = getUserOrThrow(id);
		return userMapper.toDto(user);
		
	}

	@Override
	public void deleteUser(Long id) {
		log.info("Deleting user with id: {}", id);
		User user = getUserOrThrow(id);
		userRepository.delete(user);
		log.info("User deleted successfully with id: {}", id);

	}
	private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }

	@Override
	public UserResponseDto getUserByUserName(String name) {
		User user=userRepository.findByName(name).orElseThrow(()-> new ResourceNotFoundException("user not found with this"+name));
		return userMapper.toDto(user) ;
	}
	

}

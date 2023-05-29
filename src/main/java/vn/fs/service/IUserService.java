package vn.fs.service;

import vn.fs.model.dto.UserDto;

public interface IUserService {
	public void save (UserDto userDto);
	public UserDto findByEmail (String email);
	public void updateAuthenticationType(String username, String oauth2ClientName);
}

package com.nisum.hexagonal.infrastructure.input.adapter.rest.mapper;

import com.nisum.hexagonal.domain.Phone;
import com.nisum.hexagonal.domain.User;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.PhoneRequest;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.PhoneResponse;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.UserRequest;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.dto.UserResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-09T09:15:51-0500",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        if ( request.getEmail() != null ) {
            user.setEmail( request.getEmail() );
        }
        if ( request.getName() != null ) {
            user.setName( request.getName() );
        }
        if ( request.getPassword() != null ) {
            user.setPassword( request.getPassword() );
        }
        List<Phone> list = phoneRequestListToPhoneList( request.getPhones() );
        if ( list != null ) {
            user.setPhones( list );
        }
        if ( request.getToken() != null ) {
            user.setToken( request.getToken() );
        }

        return user;
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        if ( user.getCreated() != null ) {
            userResponse.setCreated( user.getCreated() );
        }
        if ( user.getEmail() != null ) {
            userResponse.setEmail( user.getEmail() );
        }
        if ( user.getId() != null ) {
            userResponse.setId( user.getId() );
        }
        userResponse.setIsactive( user.isIsactive() );
        if ( user.getLastLogin() != null ) {
            userResponse.setLastLogin( user.getLastLogin() );
        }
        if ( user.getModified() != null ) {
            userResponse.setModified( user.getModified() );
        }
        if ( user.getName() != null ) {
            userResponse.setName( user.getName() );
        }
        if ( user.getToken() != null ) {
            userResponse.setToken( user.getToken() );
        }

        return userResponse;
    }

    @Override
    public PhoneResponse toPhoneResponse(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        PhoneResponse phoneResponse = new PhoneResponse();

        if ( phone.getCitycode() != null ) {
            phoneResponse.setCitycode( phone.getCitycode() );
        }
        if ( phone.getContrycode() != null ) {
            phoneResponse.setContrycode( phone.getContrycode() );
        }
        if ( phone.getId() != null ) {
            phoneResponse.setId( phone.getId() );
        }
        if ( phone.getNumber() != null ) {
            phoneResponse.setNumber( phone.getNumber() );
        }

        return phoneResponse;
    }

    protected Phone phoneRequestToPhone(PhoneRequest phoneRequest) {
        if ( phoneRequest == null ) {
            return null;
        }

        Phone phone = new Phone();

        if ( phoneRequest.getCitycode() != null ) {
            phone.setCitycode( phoneRequest.getCitycode() );
        }
        if ( phoneRequest.getContrycode() != null ) {
            phone.setContrycode( phoneRequest.getContrycode() );
        }
        if ( phoneRequest.getId() != null ) {
            phone.setId( phoneRequest.getId() );
        }
        if ( phoneRequest.getNumber() != null ) {
            phone.setNumber( phoneRequest.getNumber() );
        }
        if ( phoneRequest.getUser() != null ) {
            phone.setUser( toUser( phoneRequest.getUser() ) );
        }

        return phone;
    }

    protected List<Phone> phoneRequestListToPhoneList(List<PhoneRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<Phone> list1 = new ArrayList<Phone>( list.size() );
        for ( PhoneRequest phoneRequest : list ) {
            list1.add( phoneRequestToPhone( phoneRequest ) );
        }

        return list1;
    }
}

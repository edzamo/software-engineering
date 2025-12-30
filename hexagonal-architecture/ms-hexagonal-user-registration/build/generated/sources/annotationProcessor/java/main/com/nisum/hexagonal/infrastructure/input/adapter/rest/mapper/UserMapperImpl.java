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
    date = "2025-11-08T13:32:06-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.jar, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        if ( request.getName() != null ) {
            user.setName( request.getName() );
        }
        if ( request.getEmail() != null ) {
            user.setEmail( request.getEmail() );
        }
        if ( request.getPassword() != null ) {
            user.setPassword( request.getPassword() );
        }
        if ( request.getToken() != null ) {
            user.setToken( request.getToken() );
        }
        List<Phone> list = phoneRequestListToPhoneList( request.getPhones() );
        if ( list != null ) {
            user.setPhones( list );
        }

        return user;
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        if ( user.getId() != null ) {
            userResponse.setId( user.getId() );
        }
        if ( user.getName() != null ) {
            userResponse.setName( user.getName() );
        }
        if ( user.getEmail() != null ) {
            userResponse.setEmail( user.getEmail() );
        }
        if ( user.getCreated() != null ) {
            userResponse.setCreated( user.getCreated() );
        }
        if ( user.getModified() != null ) {
            userResponse.setModified( user.getModified() );
        }
        if ( user.getLastLogin() != null ) {
            userResponse.setLastLogin( user.getLastLogin() );
        }
        if ( user.getToken() != null ) {
            userResponse.setToken( user.getToken() );
        }
        userResponse.setIsactive( user.isIsactive() );

        return userResponse;
    }

    @Override
    public PhoneResponse toPhoneResponse(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        PhoneResponse phoneResponse = new PhoneResponse();

        if ( phone.getId() != null ) {
            phoneResponse.setId( phone.getId() );
        }
        if ( phone.getNumber() != null ) {
            phoneResponse.setNumber( phone.getNumber() );
        }
        if ( phone.getCitycode() != null ) {
            phoneResponse.setCitycode( phone.getCitycode() );
        }
        if ( phone.getContrycode() != null ) {
            phoneResponse.setContrycode( phone.getContrycode() );
        }

        return phoneResponse;
    }

    protected Phone phoneRequestToPhone(PhoneRequest phoneRequest) {
        if ( phoneRequest == null ) {
            return null;
        }

        Phone phone = new Phone();

        if ( phoneRequest.getId() != null ) {
            phone.setId( phoneRequest.getId() );
        }
        if ( phoneRequest.getNumber() != null ) {
            phone.setNumber( phoneRequest.getNumber() );
        }
        if ( phoneRequest.getCitycode() != null ) {
            phone.setCitycode( phoneRequest.getCitycode() );
        }
        if ( phoneRequest.getContrycode() != null ) {
            phone.setContrycode( phoneRequest.getContrycode() );
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

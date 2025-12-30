package com.nisum.hexagonal.infrastructure.output.adapter.mapper;

import com.nisum.hexagonal.domain.Phone;
import com.nisum.hexagonal.domain.User;
import com.nisum.hexagonal.infrastructure.output.repository.entity.PhoneEntity;
import com.nisum.hexagonal.infrastructure.output.repository.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-08T13:32:06-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.jar, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public User toUser(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User user = new User();

        if ( entity.getId() != null ) {
            user.setId( entity.getId().toString() );
        }
        if ( entity.getName() != null ) {
            user.setName( entity.getName() );
        }
        if ( entity.getEmail() != null ) {
            user.setEmail( entity.getEmail() );
        }
        if ( entity.getPassword() != null ) {
            user.setPassword( entity.getPassword() );
        }
        if ( entity.getCreated() != null ) {
            user.setCreated( entity.getCreated() );
        }
        if ( entity.getModified() != null ) {
            user.setModified( entity.getModified() );
        }
        if ( entity.getLastLogin() != null ) {
            user.setLastLogin( entity.getLastLogin() );
        }
        if ( entity.getToken() != null ) {
            user.setToken( entity.getToken() );
        }
        user.setIsactive( entity.isIsactive() );

        return user;
    }

    @Override
    public UserEntity toUserEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        List<PhoneEntity> list = phoneListToPhoneEntityList( user.getPhones() );
        if ( list != null ) {
            userEntity.setPhoneEntities( list );
        }
        if ( user.getId() != null ) {
            userEntity.setId( UUID.fromString( user.getId() ) );
        }
        if ( user.getName() != null ) {
            userEntity.setName( user.getName() );
        }
        if ( user.getEmail() != null ) {
            userEntity.setEmail( user.getEmail() );
        }
        if ( user.getPassword() != null ) {
            userEntity.setPassword( user.getPassword() );
        }
        if ( user.getCreated() != null ) {
            userEntity.setCreated( user.getCreated() );
        }
        if ( user.getModified() != null ) {
            userEntity.setModified( user.getModified() );
        }
        if ( user.getLastLogin() != null ) {
            userEntity.setLastLogin( user.getLastLogin() );
        }
        if ( user.getToken() != null ) {
            userEntity.setToken( user.getToken() );
        }
        userEntity.setIsactive( user.isIsactive() );

        return userEntity;
    }

    @Override
    public PhoneEntity toPhoneEntity(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        PhoneEntity phoneEntity = new PhoneEntity();

        if ( phone.getId() != null ) {
            phoneEntity.setId( phone.getId() );
        }
        if ( phone.getNumber() != null ) {
            phoneEntity.setNumber( phone.getNumber() );
        }
        if ( phone.getCitycode() != null ) {
            phoneEntity.setCitycode( phone.getCitycode() );
        }
        if ( phone.getContrycode() != null ) {
            phoneEntity.setContrycode( phone.getContrycode() );
        }

        return phoneEntity;
    }

    @Override
    public Phone toPhone(PhoneEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Phone phone = new Phone();

        if ( entity.getId() != null ) {
            phone.setId( entity.getId() );
        }
        if ( entity.getNumber() != null ) {
            phone.setNumber( entity.getNumber() );
        }
        if ( entity.getCitycode() != null ) {
            phone.setCitycode( entity.getCitycode() );
        }
        if ( entity.getContrycode() != null ) {
            phone.setContrycode( entity.getContrycode() );
        }
        if ( entity.getUser() != null ) {
            phone.setUser( toUser( entity.getUser() ) );
        }

        return phone;
    }

    protected List<PhoneEntity> phoneListToPhoneEntityList(List<Phone> list) {
        if ( list == null ) {
            return null;
        }

        List<PhoneEntity> list1 = new ArrayList<PhoneEntity>( list.size() );
        for ( Phone phone : list ) {
            list1.add( toPhoneEntity( phone ) );
        }

        return list1;
    }
}

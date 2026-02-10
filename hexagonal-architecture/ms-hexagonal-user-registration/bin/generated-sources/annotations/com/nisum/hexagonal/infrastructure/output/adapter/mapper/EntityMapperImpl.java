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
    date = "2024-11-09T09:15:50-0500",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.40.0.z20241023-1306, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public User toUser(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User user = new User();

        if ( entity.getCreated() != null ) {
            user.setCreated( entity.getCreated() );
        }
        if ( entity.getEmail() != null ) {
            user.setEmail( entity.getEmail() );
        }
        if ( entity.getId() != null ) {
            user.setId( entity.getId().toString() );
        }
        user.setIsactive( entity.isIsactive() );
        if ( entity.getLastLogin() != null ) {
            user.setLastLogin( entity.getLastLogin() );
        }
        if ( entity.getModified() != null ) {
            user.setModified( entity.getModified() );
        }
        if ( entity.getName() != null ) {
            user.setName( entity.getName() );
        }
        if ( entity.getPassword() != null ) {
            user.setPassword( entity.getPassword() );
        }
        if ( entity.getToken() != null ) {
            user.setToken( entity.getToken() );
        }

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
        if ( user.getCreated() != null ) {
            userEntity.setCreated( user.getCreated() );
        }
        if ( user.getEmail() != null ) {
            userEntity.setEmail( user.getEmail() );
        }
        if ( user.getId() != null ) {
            userEntity.setId( UUID.fromString( user.getId() ) );
        }
        userEntity.setIsactive( user.isIsactive() );
        if ( user.getLastLogin() != null ) {
            userEntity.setLastLogin( user.getLastLogin() );
        }
        if ( user.getModified() != null ) {
            userEntity.setModified( user.getModified() );
        }
        if ( user.getName() != null ) {
            userEntity.setName( user.getName() );
        }
        if ( user.getPassword() != null ) {
            userEntity.setPassword( user.getPassword() );
        }
        if ( user.getToken() != null ) {
            userEntity.setToken( user.getToken() );
        }

        return userEntity;
    }

    @Override
    public PhoneEntity toPhoneEntity(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        PhoneEntity phoneEntity = new PhoneEntity();

        if ( phone.getCitycode() != null ) {
            phoneEntity.setCitycode( phone.getCitycode() );
        }
        if ( phone.getContrycode() != null ) {
            phoneEntity.setContrycode( phone.getContrycode() );
        }
        if ( phone.getId() != null ) {
            phoneEntity.setId( phone.getId() );
        }
        if ( phone.getNumber() != null ) {
            phoneEntity.setNumber( phone.getNumber() );
        }

        return phoneEntity;
    }

    @Override
    public Phone toPhone(PhoneEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Phone phone = new Phone();

        if ( entity.getCitycode() != null ) {
            phone.setCitycode( entity.getCitycode() );
        }
        if ( entity.getContrycode() != null ) {
            phone.setContrycode( entity.getContrycode() );
        }
        if ( entity.getId() != null ) {
            phone.setId( entity.getId() );
        }
        if ( entity.getNumber() != null ) {
            phone.setNumber( entity.getNumber() );
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

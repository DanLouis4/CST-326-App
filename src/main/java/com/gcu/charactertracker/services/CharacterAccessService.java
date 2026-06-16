/**
 * Service class for managing access to character entities based on user authentication and authorization.
 * This service provides methods to check:
 * if a user is logged in
 * if they are an admin
 * if they are the owner of a character
 * if they have permission to view or manage a character
 * 
 * @author Daniel Louis
 * @version 1.0
 */

package com.gcu.charactertracker.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.gcu.charactertracker.entities.CharacterEntity;
import com.gcu.charactertracker.entities.UserEntity;

@Service
public class CharacterAccessService {

    private final UserService userService;

    public CharacterAccessService(UserService userService) {
        this.userService = userService;
    }

    public boolean isLoggedIn(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName());
    }

    public boolean isAdmin(Authentication authentication) {
        if (!isLoggedIn(authentication)) {
            return false;
        }

        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        "ROLE_ADMIN".equalsIgnoreCase(authority.getAuthority()));
    }

    public Integer getLoggedInUserId(Authentication authentication) {
        if (!isLoggedIn(authentication)) {
            return null;
        }

        UserEntity loggedInUser = userService.getUserByUsername(authentication.getName());

        return loggedInUser.getUserId();
    }

    public boolean isOwner(CharacterEntity character, Authentication authentication) {
        if (character == null || !isLoggedIn(authentication)) {
            return false;
        }

        Integer loggedInUserId = getLoggedInUserId(authentication);

        return character.getUserId() != null
                && character.getUserId().equals(loggedInUserId);
    }

    public boolean canViewCharacter(CharacterEntity character, Authentication authentication) {
        if (character == null) {
            return false;
        }

        boolean isPublic = Integer.valueOf(1).equals(character.getVisibility());

        return isPublic || isOwner(character, authentication) || isAdmin(authentication);
    }

    public boolean canManageCharacter(CharacterEntity character, Authentication authentication) {
        return isOwner(character, authentication);
    }
}
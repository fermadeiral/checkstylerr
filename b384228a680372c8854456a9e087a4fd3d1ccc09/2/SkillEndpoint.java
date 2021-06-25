package com.ciandt.techgallery.service.endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.service.SkillService;
import com.ciandt.techgallery.service.impl.SkillServiceImpl;

/**
 * Endpoint controller class for Skill requests.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Api(name = "rest", version = "v1",
    clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
    scopes = {Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE})
public class SkillEndpoint {

  private SkillService service = SkillServiceImpl.getInstance();

  /**
   * Endpoint for adding a Skill.
   * 
   * @param json with skill info.
   * @param user oauth user.
   * @return the skill added
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   */
  @ApiMethod(name = "addSkill", path = "skill", httpMethod = "post")
  public Skill addSkill(Skill skill, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException {
    return service.addOrUpdateSkill(skill, user);
  }

  /**
   * Endpoint for getting an User Skill.
   * 
   * @param id technology id.
   * @param user oauth user.
   * @return the user skill
   * @throws InternalServerErrorException when an unknown error occurs
   * @throws BadRequestException when some request parameter is wrong missing 
   * @throws OAuthRequestException when the user is not valid
   * @throws NotFoundException when the user skill is not found
   */
  @ApiMethod(name = "getUserSkill", path = "skill", httpMethod = "get")
  public Skill getUserSkill(@Named("id") String id, User user)
      throws InternalServerErrorException, BadRequestException, OAuthRequestException,
      NotFoundException {
    return service.getUserSkill(id, user);
  }

}

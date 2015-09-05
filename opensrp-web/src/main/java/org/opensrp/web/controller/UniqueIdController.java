package org.opensrp.web.controller;

import com.google.gson.Gson;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.opensrp.common.util.HttpAgent;
import org.opensrp.common.util.HttpResponse;

import org.opensrp.dto.LastIdDTO;
import org.opensrp.dto.UniqueIdDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

import static ch.lambdaj.collection.LambdaCollections.with;
import static org.opensrp.web.HttpHeaderFactory.allowOrigin;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class UniqueIdController {
    private static Logger logger = LoggerFactory.getLogger(UniqueIdController.class.toString());
    private final String drishtiUniqueIdURL;
    private final String lastUsedIdURL;
    private final String lastIdUrl;
    private UserController userController;
    private HttpAgent httpAgent;

    @Autowired
    public UniqueIdController(@Value("#{opensrp['opensrp.anm.uniqueid.url']}") String drishtiUniqueIdURL,
                              @Value("#{opensrp['opensrp.anm.lastUsedId.url']}") String lastUsedIdURL,
                              @Value("#{opensrp['opensrp.anm.lastId.url']}") String lastIdUrl,
                              UserController userController,
                              HttpAgent httpAgent) {
        this.lastUsedIdURL = lastUsedIdURL;
        this.drishtiUniqueIdURL = drishtiUniqueIdURL;
        this.userController = userController;
        this.httpAgent = httpAgent;
        this.lastIdUrl = lastIdUrl;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unique-id")
    @ResponseBody
    public ResponseEntity<UniqueIdDTO> uniqueIdForANM() {
        HttpResponse response = new HttpResponse(false, null);
        try {
            String anmIdentifier = userController.currentUser().getUsername();
            response = httpAgent.get(drishtiUniqueIdURL + "?anm-id=" + anmIdentifier);
            UniqueIdDTO dto = new Gson().fromJson(response.body(), UniqueIdDTO.class);
            logger.info("Fetched unique id for ANM: " + anmIdentifier);
            return new ResponseEntity<UniqueIdDTO>(dto, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error(MessageFormat.format("{0} occurred while fetching Unique ID for anm. StackTrace: \n {1}", exception.getMessage(), ExceptionUtils.getFullStackTrace(exception)));
            logger.error(MessageFormat.format("Response with status {0} and body: {1} was obtained from {2}", response.isSuccess(), response.body(), drishtiUniqueIdURL));
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/last-used-id")
    @ResponseBody
    public ResponseEntity<LastIdDTO> lastUsedIdForUser() {
        HttpResponse response = new HttpResponse(false, null);
        try {
            String anmIdentifier = userController.currentUser().getUsername();
            response = httpAgent.get(lastUsedIdURL + "?anm-id=" + anmIdentifier);
            LastIdDTO dto = new Gson().fromJson(response.body(), LastIdDTO.class);
            logger.info("Fetched last used id for ANM: " + anmIdentifier);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(MessageFormat.format("{0} occurred while fetching last used Unique ID for anm. StackTrace: \n {1}", e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
            logger.error(MessageFormat.format("Response with status {0} and body: {1} was obtained from {2}", response.isSuccess(), response.body(), lastUsedIdURL));
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(headers = {"Accept=application/json"}, method = POST, value = "/last-id")
    public ResponseEntity<LastIdDTO> setLastId(@RequestParam("anm-id") String anmIdentifier, @RequestParam("last-id") String lastId) {
        HttpResponse response = new HttpResponse(false, null);
        try {
            response = httpAgent.post(lastIdUrl + "?anm-id=" + anmIdentifier + "&last-id=" +lastId, "", "application/json");
            LastIdDTO dto = new Gson().fromJson(response.body(), LastIdDTO.class);
            logger.info("Saved last used id for ANM: " + anmIdentifier);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(MessageFormat.format("{0} occurred while saving last used Unique ID for anm. StackTrace: \n {1}", e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
            logger.error(MessageFormat.format("Response with status {0} and body: {1} was obtained from {2}", response.isSuccess(), response.body(), lastUsedIdURL));
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }
}
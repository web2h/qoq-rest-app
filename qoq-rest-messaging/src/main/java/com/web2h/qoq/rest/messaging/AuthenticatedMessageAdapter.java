package com.web2h.qoq.rest.messaging;

import com.web2.qoq.rest.model.entity.user.User;
import com.web2h.qoq.rest.service.error.ApplicationException;

public interface AuthenticatedMessageAdapter<Req extends QoqRequest, Res extends QoqResponse> {

	Res callService(Req request, User user) throws ApplicationException;
}

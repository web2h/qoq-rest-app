package com.web2h.qoq.rest.messaging;

import com.web2h.qoq.rest.service.error.ApplicationException;

public interface MessageAdapter<Req extends QoqRequest, Res extends QoqResponse> {

	Res callService(Req request) throws ApplicationException;
}

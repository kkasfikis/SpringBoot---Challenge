package com.kkasfikis.quintessential.challenge.jwt.payload.request;

import javax.validation.constraints.Min;

public class RemoveCommentRequest {
	@Min(1)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}

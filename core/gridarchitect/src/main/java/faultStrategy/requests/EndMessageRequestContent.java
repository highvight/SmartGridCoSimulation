/*
 * Copyright (c) 2011-2015, fortiss GmbH.
 * Licensed under the Apache License, Version 2.0.
 *
 * Use, modification and distribution are subject to the terms specified
 * in the accompanying license file LICENSE.txt located at the root directory
 * of this software distribution.
 */

package faultStrategy.requests;

import akka.basicMessages.RequestContent;

/**
 * EndMessage
 * 17.07.2014
 * @author Luc
 * Should not be send to anyone, but this message indicates that the strategy is over
 *
 */
public class EndMessageRequestContent implements RequestContent{
	
	@Override
	public String toString()
	{
		return "EndMessage";
	}
}

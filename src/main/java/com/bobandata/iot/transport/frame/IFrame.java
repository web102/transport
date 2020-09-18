package com.bobandata.iot.transport.frame;

import io.netty.buffer.ByteBuf;

public abstract interface IFrame extends Cloneable, IExplain
{
  public abstract int encode()
    throws Exception;

  public abstract int decode()
    throws Exception;

  public abstract ByteBuf getBuffer();
}


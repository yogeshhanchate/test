package com.newgen.formApplet.Controls.InputVerifier;

public abstract interface NGTextComponent
{
  public abstract String getText();

  public abstract void setText(String paramString);

  public abstract String getName();

  public abstract int getCaretPosition();

  public abstract void setCaretPosition(int paramInt);

  public abstract String getSelectedText();

  public abstract int getSelectionEnd();

  public abstract void setSelectionEnd(int paramInt);

  public abstract int getSelectionStart();

  public abstract void setSelectionStart(int paramInt);
}
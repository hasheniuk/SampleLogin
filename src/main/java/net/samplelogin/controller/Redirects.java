package net.samplelogin.controller;

public final class Redirects {
    public static final String AUTH = "redirect:/auth";
    public static final String PROFILE = "redirect:/profile";
    public static final String SIGN_OUT = "redirect:/";
    public static final String DISCONNECT = "redirect:/connect/disconnect/all";

    private Redirects() {}
}

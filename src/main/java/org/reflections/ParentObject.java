package org.reflections;

import java.util.Map;

public abstract class ParentObject {
    private Map<Integer,String> maps = null;
    private Long number = 4L;
    private NUriScheme uriScheme;

    public Map<Integer, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<Integer, String> maps) {
        this.maps = maps;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public NUriScheme getUriScheme() {
        return uriScheme;
    }

    public void setUriScheme(NUriScheme uriScheme) {
        this.uriScheme = uriScheme;
    }
}

package com.tectonic.klaviyo;

import com.getcapacitor.Logger;

public class TectonicKlaviyo {

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }
}

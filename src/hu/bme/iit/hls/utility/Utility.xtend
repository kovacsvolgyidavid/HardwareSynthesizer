package hu.bme.iit.hls.utility

import java.util.List
import java.util.stream.Collectors

class Utility {
        def static filterList(List<?> list, Class<?> clazz, boolean isComplementer){
        return list.stream.filter(e | 
            if(!isComplementer){
                clazz.isInstance(e)
            }
            else{
                !clazz.isInstance(e)
            }
        ).collect(Collectors.toList);
        
    }
}
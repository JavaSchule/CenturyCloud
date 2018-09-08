package net.byteexception.cloud.lib.documents;

import java.io.File;

public interface Document{

    void create( File file );

    void save();

    void append( Object object );

}

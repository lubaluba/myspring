package org.litespring.core.io;
import java.io.IOException;
import java.io.InputStream;
/**
 *	这是一个资源类,它是一个抽象。
 *	它可以通过多个不同的实现类,来实现从多种地方获取到资源,比如Filesystem和classpath,甚至url上
 *	getInputStream():无论我们从哪里获取的资源,我们都应当有方法去获得InputStream去读资源
 */
public interface Resource {
	InputStream getInputStream() throws IOException;
	String getDescription();
}

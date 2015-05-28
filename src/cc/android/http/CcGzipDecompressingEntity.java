package cc.android.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.HttpEntity;

/**
 * Http解压内容
 * 
 *
 */
public class CcGzipDecompressingEntity extends HttpEntityWrapper {

	public CcGzipDecompressingEntity(final HttpEntity entity) {
		super(entity);
	}

	public InputStream getContent() throws IOException, IllegalStateException {
		InputStream wrappedin = wrappedEntity.getContent();
		return new GZIPInputStream(wrappedin);
	}

	public long getContentLength() {
		return -1;
	}
}
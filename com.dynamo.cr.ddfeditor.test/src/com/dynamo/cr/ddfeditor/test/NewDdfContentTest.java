package com.dynamo.cr.ddfeditor.test;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.lang.reflect.Method;

import org.junit.Test;

import com.dynamo.cr.editor.core.EditorCorePlugin;
import com.dynamo.cr.editor.core.IResourceType;
import com.dynamo.cr.editor.core.IResourceTypeRegistry;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.TextFormat;

public class NewDdfContentTest {

    @Test
    public void test() throws Throwable {
        // We create messages here in order to check that we aren't missing any required fields

        IResourceTypeRegistry registry = EditorCorePlugin.getDefault().getResourceTypeRegistry();
        IResourceType[] resourceTypes = registry.getResourceTypes();

        // Ensure that we test at least one
        assertTrue(resourceTypes.length > 1);

        for (IResourceType t : resourceTypes) {
            System.out.println(t.getName());
            Class<GeneratedMessage> messageClass = t.getMessageClass();
            byte[] templateData = t.getTemplateData();
            if (messageClass != null) {

                Method m = messageClass.getDeclaredMethod("newBuilder");
                @SuppressWarnings("rawtypes")
                GeneratedMessage.Builder builder = (Builder) m.invoke(null);

                StringReader reader = new StringReader(new String(templateData));
                TextFormat.merge(reader, builder);
            }
        }
    }
}

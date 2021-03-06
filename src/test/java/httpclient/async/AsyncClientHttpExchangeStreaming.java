/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package httpclient.async;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.protocol.HttpContext;

/**
 * 内容传输的异步HTTP交换
 * 此示例演示了具有完整内容流的异步HTTP请求/响应交换。
 * This example demonstrates an asynchronous HTTP request / response exchange with
 * a full content streaming.
 */
public class AsyncClientHttpExchangeStreaming {

    public static void main(final String[] args) throws Exception {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            httpclient.start();
            Future<Boolean> future = httpclient.execute(
                    HttpAsyncMethods.createGet("http://httpbin.org/"),
                    new MyResponseConsumer(), new FutureCallback<Boolean>() {
                        @Override
                        public void completed(Boolean result) {
                            System.out.println("completed");
                        }

                        @Override
                        public void failed(Exception ex) {
                            System.out.println("failed");
                        }

                        @Override
                        public void cancelled() {
                            System.out.println("cancelled");

                        }
                    });
            Boolean result = future.get();
            if (result != null && result.booleanValue()) {
                System.out.println("Request successfully executed");
            } else {
                System.out.println("Request failed");
            }
            System.out.println("Shutting down");
        } finally {
            httpclient.close();
        }
        System.out.println("Done");
    }

    static class MyResponseConsumer extends AsyncCharConsumer<Boolean> {

        @Override
        protected void onResponseReceived(final HttpResponse response) {
            System.out.println("onResponseReceived");
        }

        @Override
        protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
            System.out.println("onCharReceived");
//            while (buf.hasRemaining()) {
//                System.out.print(buf.get());
//            }
        }

        @Override
        protected void releaseResources() {
            System.out.println("releaseResources");
        }

        @Override
        protected Boolean buildResult(final HttpContext context) {
            System.out.println("buildResult");
            return Boolean.TRUE;
        }

    }

}
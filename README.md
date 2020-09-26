## 一、基于 HTTP 协议的实现
### 服务端实现（com.shadow.framework.http.server.*）
   ![tomcat架构图](./tomcat.png)
```java
        Tomcat tomcat = new Tomcat();

		Server server = tomcat.getServer();
		Service service = server.findService("Tomcat");

		Connector connector = new Connector();
		connector.setPort(url.getPort());

		Engine engine = new StandardEngine();
		engine.setDefaultHost(url.getHostname());

		Host host = new StandardHost();
		host.setName(url.getHostname());

		String contextPath = "";
		Context context = new StandardContext();
		context.setPath(contextPath);
		context.addLifecycleListener(new Tomcat.FixContextListener());

		host.addChild(context);
		engine.addChild(host);

		service.setContainer(engine);
		service.addConnector(connector);

		tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet());
		context.addServletMappingDecoded("/*","dispatcher");

		try {
			tomcat.start();
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
```

### 客户端实现
```java
        // 网络传输
		try {
			URL url = new URL("http", innerUrl.getHostname(), innerUrl.getPort(), "/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			// 发送数据
			OutputStream outputStream = connection.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(invocation);
			oos.flush();
			oos.close();

			// 接收返回的数据并返回
			InputStream inputStream = connection.getInputStream();
			String result = IOUtils.toString(inputStream,"utf-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
```


## 二、基于 Dubbo（Netty）协议的实现
### 服务端实现（com.shadow.framework.dubbo.server.*）
   netty的基本使用
```java
		NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
		NioEventLoopGroup workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup,workGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new StringEncoder())
								.addLast(new StringDecoder())
								.addLast(new DubboServerHandler());
					}
				});

		try {
			ChannelFuture channelFuture = serverBootstrap.bind(hostname, port).sync();
			System.out.println("nettyCore server started on hostname " + hostname + " and port on " + port);
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
```

### 客户端实现
```java
	// 线程池
	private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	// 任务-也是业务处理类
	private DubboClientHandler dubboClientHandler;
	// 执行的调用方法
	public Object send(URL url, Invocation invocation) {
		// init
		if(dubboClientHandler == null){
			initDubbo(url.getHostname(),url.getPort());
		}
		// 请求参数设置
		dubboClientHandler.setInvocation(invocation);
		try {
			// 线程池同步执行获取结果
			return executorService.submit(dubboClientHandler).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}


	private void initDubbo(String hostname,int port) {

		dubboClientHandler = new DubboClientHandler();

		NioEventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
				.option(ChannelOption.TCP_NODELAY,true)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new StringEncoder())
								.addLast(new StringDecoder())
								.addLast(dubboClientHandler);
					}
				});
		try {
			bootstrap.connect(hostname,port).sync();
			System.out.println("connect to hostname " + hostname + " port " + port);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

```

## Dubbo 原理
   ![Dubbo](./dubbo.png)
   
   ![Dubbo架构图](./)
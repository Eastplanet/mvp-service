// config-overrides.js
module.exports = function override(config, env) {
  console.log('Overriding devServer configuration');
  config.devServer = {
    ...config.devServer,
    host: 'localhost',
    port: 8081,
    client: {
      webSocketURL: { hostname: undefined, pathname: undefined, port: '0' },
    },
  };
  config.resolve = {
    ...config.resolve,
    alias: {
        ...config.resolve.alias,
        'faye-websocket': require.resolve('./src/empty-module.js'),
        'sockjs-client': require.resolve('./src/empty-module.js'),
        'websocket-driver': require.resolve('./src/empty-module.js'),
        'websocket-extensions': require.resolve('./src/empty-module.js'),
    },
  };
  return config;
};

// config-overrides.js
module.exports = function override(config, env) {
    console.log('Overriding devServer configuration');
    config.devServer = {
      ...config.devServer,
      host: 'localhost',
      port: 8081,
    };
    console.log('-------------------------------------------------');
    console.log('devServer configuration:', config.devServer);
    console.log('-------------------------------------------------');
    return config;
  };
  
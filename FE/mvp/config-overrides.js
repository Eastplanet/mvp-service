// config-overrides.js
module.exports = function override(config, env) {
    console.log('Overriding devServer configuration'); // 로그 추가
    config.devServer = {
      ...config.devServer,
      disableHostCheck: true,
      allowedHosts: 'all',
    };
    console.log('-------------------------------------------------');
    console.log('devServer configuration:', config.devServer);
    console.log('-------------------------------------------------');
    return config;
  };
  
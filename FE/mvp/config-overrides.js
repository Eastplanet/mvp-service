// config-overrides.js
module.exports = function override(config, env) {
    console.log('Overriding devServer configuration'); // 로그 추가
    config.devServer = {
      ...config.devServer,
      host: 'mvp-project.shop', // 호스트 이름
      port: 8081,               // 포트 번호
      allowedHosts: [
        'mvp-project.shop'      // 허용된 호스트에 도메인 추가
      ],
      disableHostCheck: true,
    };
    console.log('-------------------------------------------------');
    console.log('devServer configuration:', config.devServer);
    console.log('-------------------------------------------------');
    return config;
  };
  
from setuptools import setup, find_packages

setup(
    name='parking_bot',
    version='0.1',
    packages=find_packages(where='src'),
    package_dir={'': 'src'},
    install_requires=[
        'paho-mqtt',
    ],
    entry_points={
        'console_scripts': [
            'parking_bot = main:main',
        ],
    },
)

-- ɾ�����ݿ⣨������ڵĻ���
drop database if exists spring_security_quick_start;

-- ����һ���µ����ݿ�
create database spring_security_quick_start;

-- �л����մ��������ݿ�
use spring_security_quick_start;

-- ���� oauth2_registered_client �����ڴ洢ע��Ŀͻ�����Ϣ
CREATE TABLE oauth2_registered_client (
    id varchar(100) NOT NULL,  -- �ͻ���Ψһ��ʶ��
    client_id varchar(100) NOT NULL,  -- �ͻ��� ID
    client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- �ͻ��� ID ����ʱ��
    client_secret varchar(200) DEFAULT NULL,  -- �ͻ�����Կ
    client_secret_expires_at timestamp DEFAULT NULL,  -- �ͻ�����Կ����ʱ��
    client_name varchar(200) NOT NULL,  -- �ͻ�������
    client_authentication_methods varchar(1000) NOT NULL,  -- �ͻ�����֤����
    authorization_grant_types varchar(1000) NOT NULL,  -- ��Ȩ��Ȩ����
    redirect_uris varchar(1000) DEFAULT NULL,  -- �ض��� URI
    post_logout_redirect_uris varchar(1000) DEFAULT NULL,  -- ע������ض��� URI
    scopes varchar(1000) NOT NULL,  -- �ͻ�����Ȩ��Χ
    client_settings varchar(2000) NOT NULL,  -- �ͻ�������
    token_settings varchar(2000) NOT NULL,  -- ��������
    PRIMARY KEY (id)  -- ����
);

-- ���� oauth2_authorization_consent �����ڴ洢�û��Կͻ��˵���Ȩͬ��
CREATE TABLE oauth2_authorization_consent (
    registered_client_id varchar(100) NOT NULL,  -- ע��Ŀͻ��� ID
    principal_name varchar(200) NOT NULL,  -- �û�������
    authorities varchar(1000) NOT NULL,  -- �û�Ȩ��
    PRIMARY KEY (registered_client_id, principal_name)  -- ����
);

-- 
-- IMPORTANT:
-- If using PostgreSQL, update ALL columns defined with 'blob' to 'text',
-- as PostgreSQL does not support the 'blob' data type.
-- 

-- ���� oauth2_authorization �����ڴ洢��Ȩ��ص���Ϣ
CREATE TABLE oauth2_authorization (
    id varchar(100) NOT NULL,  -- ��Ȩ��¼��Ψһ��ʶ��
    registered_client_id varchar(100) NOT NULL,  -- ע��Ŀͻ��� ID
    principal_name varchar(200) NOT NULL,  -- �û�������
    authorization_grant_type varchar(100) NOT NULL,  -- ��Ȩ����
    authorized_scopes varchar(1000) DEFAULT NULL,  -- ��Ȩ��Χ
    attributes blob DEFAULT NULL,  -- ��Ȩ����
    state varchar(500) DEFAULT NULL,  -- ��Ȩ״̬
    authorization_code_value blob DEFAULT NULL,  -- ��Ȩ��ֵ
    authorization_code_issued_at timestamp DEFAULT NULL,  -- ��Ȩ�뷢��ʱ��
    authorization_code_expires_at timestamp DEFAULT NULL,  -- ��Ȩ�����ʱ��
    authorization_code_metadata blob DEFAULT NULL,  -- ��Ȩ��Ԫ����
    access_token_value blob DEFAULT NULL,  -- ��������ֵ
    access_token_issued_at timestamp DEFAULT NULL,  -- �������Ʒ���ʱ��
    access_token_expires_at timestamp DEFAULT NULL,  -- �������ƹ���ʱ��
    access_token_metadata blob DEFAULT NULL,  -- ��������Ԫ����
    access_token_type varchar(100) DEFAULT NULL,  -- ������������
    access_token_scopes varchar(1000) DEFAULT NULL,  -- �������Ʒ�Χ
    oidc_id_token_value blob DEFAULT NULL,  -- OIDC ID ����ֵ
    oidc_id_token_issued_at timestamp DEFAULT NULL,  -- OIDC ID ���Ʒ���ʱ��
    oidc_id_token_expires_at timestamp DEFAULT NULL,  -- OIDC ID ���ƹ���ʱ��
    oidc_id_token_metadata blob DEFAULT NULL,  -- OIDC ID ����Ԫ����
    refresh_token_value blob DEFAULT NULL,  -- ˢ������ֵ
    refresh_token_issued_at timestamp DEFAULT NULL,  -- ˢ�����Ʒ���ʱ��
    refresh_token_expires_at timestamp DEFAULT NULL,  -- ˢ�����ƹ���ʱ��
    refresh_token_metadata blob DEFAULT NULL,  -- ˢ������Ԫ����
    user_code_value blob DEFAULT NULL,  -- �û�����ֵ
    user_code_issued_at timestamp DEFAULT NULL,  -- �û����뷢��ʱ��
    user_code_expires_at timestamp DEFAULT NULL,  -- �û��������ʱ��
    user_code_metadata blob DEFAULT NULL,  -- �û�����Ԫ����
    device_code_value blob DEFAULT NULL,  -- �豸����ֵ
    device_code_issued_at timestamp DEFAULT NULL,  -- �豸���뷢��ʱ��
    device_code_expires_at timestamp DEFAULT NULL,  -- �豸�������ʱ��
    device_code_metadata blob DEFAULT NULL,  -- �豸����Ԫ����
    PRIMARY KEY (id)  -- ����
);
